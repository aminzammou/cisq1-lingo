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
Onze aplicatie's slaan verschillende soorten data op sommige van die data kunnen gevoelige
gegevens zijn en die willen we goed beschermen maar dat gaat niet altijd goed.
Een attaker kan goed gebruik maken van die gegevens, en kan op verschillende manieren hier aan komen
deze zijn:

-   Doordat de encryptie en decryptie worden door de database gedaan, de attacker kan door middel van sql injection
    de data achterhalen. Zo zien we ook al pas je encryptie toe, wanneer deze door de database gedaan kan dat de attakker helpen en is de encryptie niet meer een hulp middel.

-   Man-in-the-middel : Wanneer een site niet of nauwlijks gebruik maakt van TLS , of een zwakke encryptie heeft
kunnen attackers het netwerkverkeer in de gaten houden en verbindingen door sturen naar http inplaats van https.
    Door doordat er niet gebruik van https maar http wordt gemaakt is deze niet meer veilig en kan attaker 
    gegevens zoals cookies stelen.
    
-   Plain text of zwake gehaste wachtwoorden: Door dat gevoelige gegevens worden opgeslagen als normale text of de gevoelige gegevens worden wel gehasht maar niet heel goed.
### Risk
In veel applicaties word gevoelige data opgeslagen denk maar aan: de bank, de ggd, de overheid en zelfs school.
Wanneer deze gestolen of gelekt worden zullen er grote geloven aan hangen, deze gegevens kunne gebruikt worden om ergens binnen te komen of ze worden doorverkocht.

In ons geval werken we niet zo zeker met gevoelige gegevens, maar een speler kan wel
valsspelen door het te raden woord te zien te krijgen en hiermee vals te spelen.

### Counter-measures
-   Classify Data : breng in kaart wat voor gegevens er verwerkt wordt, kijk vervolgens
of het nodig is om die data op te slaan. 
    
-   Encryptie: Zorg er voor dat de gevoelige data encrypt is.

-   TLS en HTTPS: Maak gebruik van protocollen als TlS, en gebruik https en niet http.
-   Github Secrets: Als de code online op github staat, probeer dan gebruik te maken van Github secrets.  

In dit project wordt gebruik gemaakt van github secrets om gevoelige gegevens niet zomaar op internet te delen,
wanneer deze gegevens op internet staan kan iemand hier gebruik van gaan maken.