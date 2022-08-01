CREATE TABLE `teste`.`authorisbn` (
  `AuthorID` INT NOT NULL,
  `ISBN` INT NOT NULL,
  PRIMARY KEY (`AuthorID`, `ISBN`),
  INDEX `ISBN_idx` (`ISBN` ASC) VISIBLE,
  CONSTRAINT `AuthorID`
    FOREIGN KEY (`AuthorID`)
    REFERENCES `teste`.`authors` (`AuthorID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ISBN`
    FOREIGN KEY (`ISBN`)
    REFERENCES `teste`.`titles` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
