DROP TABLE IF EXISTS Calendar
;
CREATE TABLE Calendar(
    Id                  INT                 PRIMARY KEY,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    LastUpdated         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP()
    )
;


DROP TABLE IF EXISTS CalendarTemplate
;
CREATE TABLE CalendarTemplate(
    Id                  INT                 PRIMARY KEY,
    CalendarId          INT                 NOT NULL,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    StartDate           DATE                NOT NULL,
    EndDate             DATE                NOT NULL,
    RecurrenceSetting   VARCHAR(255)        NOT NULL,
    LastUpdated         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP(),

    FOREIGN KEY(CalendarId) REFERENCES Calendar(Id)
    )
;


DROP TABLE IF EXISTS CalendarEntry
;
CREATE TABLE CalendarEntry(
    Id                  INT                 PRIMARY KEY,
    CalendarId          INT                 NOT NULL,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    Date                DATE                NOT NULL,
    TemplateId          INT                 NULL,
    LastUpdated         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP(),

    FOREIGN KEY(CalendarId) REFERENCES Calendar(Id),
    FOREIGN KEY(TemplateId) REFERENCES CalendarTemplate(Id)
    )
;


DROP TABLE IF EXISTS Portfolio
;
CREATE TABLE Portfolio(
    Id                  INT                 PRIMARY KEY,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    RegType             VARCHAR(8)          NOT NULL,
    IssuerId            VARCHAR(8)          NOT NULL,
    Tag                 VARCHAR(64)         NULL,
    LastUpdated         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP(),

    FOREIGN KEY(IssuerId) REFERENCES SecurityIssuer(Id)
    )
;


DROP TABLE IF EXISTS Security
;
CREATE TABLE Security(
    Id                  INT                 PRIMARY KEY,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    Type                VARCHAR(8)          NOT NULL,
    IssuerId            VARCHAR(8)          NOT NULL,
    IssuerCode          VARCHAR(16)         NOT NULL,
    GoogleCode          VARCHAR(16)         NULL,
    DistributionSetting VARCHAR(255)        NULL,
    LastUpdated         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP(),

    FOREIGN KEY(IssuerId) REFERENCES SecurityIssuer(Id)
    )
;


DROP TABLE IF EXISTS SecurityHolding
;
CREATE TABLE SecurityHolding(
    Id                  INT                 PRIMARY KEY,
    PortfolioId         INT                 NOT NULL,
    SecurityId          INT                 NOT NULL,
    Units               DECIMAL(12, 4)      NOT NULL,
    TotalContribution   DECIMAL(12, 4)      NOT NULL,
    LastUpdated         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP(),

    FOREIGN KEY(PortfolioId) REFERENCES Portfolio(Id),
    FOREIGN KEY(SecurityId) REFERENCES Security(Id)
    )
;
