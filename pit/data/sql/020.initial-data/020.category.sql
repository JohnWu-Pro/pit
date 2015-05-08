INSERT INTO SecurityIssuer(Id, Name, Description)
VALUES('TDB', 'TD Bank', 'TD Bank')
;
INSERT INTO SecurityIssuer(Id, Name, Description)
VALUES('AGF', 'AGF Management Limited', 'AGF Management Limited')
;
INSERT INTO SecurityIssuer(Id, Name, Description)
VALUES('SLF', 'Sun Life Financial', 'Sun Life Financial')
;

INSERT INTO DistributionRule(Id, Name, Description, ClassName, ParamsCsv)
VALUES('APU008', '$0.08 per Unit', '$0.08 per Unit', 'org.wjh.pit.rule.AmountPerUnit', '0.08')
;
INSERT INTO DistributionRule(Id, Name, Description, ClassName, ParamsCsv)
VALUES('APU010', '$0.10 per Unit', '$0.10 per Unit', 'org.wjh.pit.rule.AmountPerUnit', '0.10')
;
