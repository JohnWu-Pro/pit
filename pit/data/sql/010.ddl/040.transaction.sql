DROP TABLE IF EXISTS StandingTransaction
;
CREATE TABLE StandingTransaction(
    Id                  BIGINT              PRIMARY KEY,
    ProfolioId          INT                 NOT NULL,
    SecurityId          INT                 NOT NULL,
    StartDate           DATE                NOT NULL,
    EndDate             DATE                NOT NULL,
    RecurrenceSetting   VARCHAR(255)        NOT NULL,
    Unit                DECIMAL(12, 4)      NULL,
    Amount              DECIMAL(12, 4)      NULL,
    TypeId              VARCHAR(8)          NOT NULL
    LastUpdated         TIMESTAMP           NOT NULL,

    FOREIGN KEY(PortfolioId) REFERENCES Portfolio(Id),
    FOREIGN KEY(SecurityId) REFERENCES Security(Id)
    )
;


DROP TABLE IF EXISTS Transaction
;
CREATE TABLE Transaction(
    Id                  BIGINT              PRIMARY KEY,
    ProfolioId          INT                 NOT NULL,
    SecurityId          INT                 NOT NULL,
    StandingTxId        INT                 NULL,
    Date                DATE                NOT NULL,
    Unit                DECIMAL(12, 4)      NOT NULL,
    Price               DECIMAL(12, 4)      NOT NULL,
    Amount              DECIMAL(12, 4)      NOT NULL,
    TypeId              VARCHAR(8)            NOT NULL,
    SourceId            VARCHAR(8)            NOT NULL,
    LastUpdated         TIMESTAMP           NOT NULL,

    FOREIGN KEY(PortfolioId) REFERENCES Portfolio(Id),
    FOREIGN KEY(SecurityId) REFERENCES Security(Id),
    FOREIGN KEY(StandingTxId) REFERENCES StandingTransaction(Id)
    )
;


DROP TABLE IF EXISTS SecurityPrice
;
CREATE TABLE SecurityPrice(
    SecurityId          INT                 NOT NULL,
    Date                DATE                NOT NULL,
    Price               DECIMAL(12, 4)      NOT NULL,
    LastUpdated         TIMESTAMP           NOT NULL,

    PRIMARY KEY(SecurityId, Date),

    FOREIGN KEY(SecurityId) REFERENCES Security(Id)
    )
;
