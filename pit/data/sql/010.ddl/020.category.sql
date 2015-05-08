DROP TABLE IF EXISTS SecurityIssuer
;
CREATE TABLE SecurityIssuer(
    Id                  VARCHAR(64)         PRIMARY KEY,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL
    )
;


DROP TABLE IF EXISTS DistributionRule
;
CREATE TABLE DistributionRule(
    Id                  VARCHAR(64)         PRIMARY KEY,
    Name                VARCHAR(64)         NOT NULL,
    Description         VARCHAR(255)        NULL,
    ClassName           VARCHAR(64)         NOT NULL,
    ParamsCsv           VARCHAR(255)        NULL
    )
;
