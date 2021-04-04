# Vulnerability Analysis

## A9:2017 Using Components with Known Vulnerabilities

### Description
Wanneer we beginnen met een project kiezen we voor componenten om te gebruiken in onze applicatie, deze componenten hebben kwetsbaarheden.
Deze kwetsbaarheden kunnen worden gevonden door ze op internet te zoeken, op sites die CVE lijsten bijhouden.
In een CVE lijst worden kwetsbaarheden opgenomen met de tijdsslot en id.

Het positieve is dat we eten wat deze kwetsbaarheden zijn en hier iets kunnen doen, het negatieve is dat attackers ook weten 
wat deze kwetsbaarheden zijn en hier iets mee kunnen doen als wij er geen oplossing voor zoeken.

### Risk
Er zullen altijd kwetsbaarheden zitten aan de software die we gebruiken. Er zullen daarom altijd attackers zijn die hier gebruik
van zullen proberen te maken, dit doen ze door eerst te onderzoeken wat er allemaal gebruikt word in de applicatie. Zodra ze weten wat er gebruikt 
word in combinatie met de versie ervan, kunnen ze middels de CVE lijsten op internet, de kwetsbaarheden vinden die horen bij die component en hier op inspelen.

### Counter-measures
-   Maak een lijst aan componenten die gebruikt worden binnen de applicatie, 
zodat het duidelijk is waar gebruik van wordt gemaakt.

-   Download de software van de original bron of van een betrouwbare bron.

-   Zorg er voor dat de componenten altijd up-to-date zijn, maak hierbij gebruik van Dependency-cheker of van Dependabot.

In dit project wordt er gebruik gemaakt van Dependency-cheker en van Dependabot. 
Dependabot is supper handig om te gebruiken, omdat het een pull request creëert voor 
componenten die outdated zijn geworden.

## A3:2017 Sensitive Data Exposure

### Description

### Risk

### Counter-measures
-   