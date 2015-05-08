DROP TABLE IF EXISTS Enum
;
CREATE TABLE Enum(
    Id                  VARCHAR(64)         PRIMARY KEY,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    ParamNames          VARCHAR(255)        NULL,
    ParamTypes          VARCHAR(255)        NULL
    )
;


DROP TABLE IF EXISTS EnumItem
;
CREATE TABLE EnumItem(
    EnumId              VARCHAR(64)         NOT NULL,
    Id                  VARCHAR(8)          NOT NULL,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    Params              VARCHAR(255)        NULL,

    PRIMARY KEY(EnumId, Id),

    FOREIGN KEY(EnumId) REFERENCES Enum(Id)
    )
;
