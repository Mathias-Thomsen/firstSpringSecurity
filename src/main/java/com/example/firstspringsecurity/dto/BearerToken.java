package com.example.firstspringsecurity.dto;
import lombok.Data;

@Data
public class BearerToken {
    private String accessToken;
    private String tokenType;

    public BearerToken(String accessToken, String tokenType) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

}

/*
 * Denne klasse ser ud til at være designet som en DTO (Data Transfer Object), og den bruges sandsynligvis i sammenhæng med JWT (JSON Web Token)-baseret autentificering. Her er nogle grundlæggende koncepter og formål med denne type klasse:
 * Formål med klassen:
 * Tokenhåndtering:
 * Denne klasse repræsenterer en bærertoken, som normalt bruges i autentificeringsscenarier. Bærertokens, især i forbindelse med JWT, indeholder information om en bruger eller en enhed, der er blevet autentificeret.

 * DTO (Data Transfer Object):
 * DTO'er bruges ofte til at overføre data mellem lag i en applikation. I dette tilfælde er BearerToken sandsynligvis designet til at overføre tokenrelaterede oplysninger fra en del af systemet til en anden. DTO'er hjælper med at organisere og simplificere kommunikationen mellem forskellige komponenter eller lag af en applikation.

 * Brugen af klassen:

 * Autentificering:
 * Når en bruger eller enhed har gennemført en vellykket autentificering, genereres ofte en bærertoken, som indeholder oplysninger om autentificeringen og måske også autorisation. Denne klasse kan bruges til at repræsentere disse tokens.

 * Overførsel af token: Hvis forskellige dele af applikationen har brug for at arbejde med eller have adgang til autentificeringstokenet, kan BearerToken-klassen bruges til at overføre disse oplysninger. DTO'er fungerer som en container for data, der skal overføres mellem komponenter.
     Lombok @Data for bekvemmelighed: Anvendelsen af Lombok's @Data-annotation gør det muligt at generere standardmetoder (f.eks. gettere, settere, toString(), osv.), hvilket reducerer mængden af boilerplate-kode, der normalt er forbundet med disse typer klasser.

 Sammenfattende bruges denne klasse sandsynligvis til at repræsentere og overføre autentificeringstokens i en Java-applikation, og det bliver som en DTO for at lette og organisere kommunikationen mellem forskellige dele af systemet.
 */
