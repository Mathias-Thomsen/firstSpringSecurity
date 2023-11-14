package com.example.firstspringsecurity.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {

    //it's a Data Trasfer Object for Login
    private String email ;
    private String password ;
}



/*
Denne klasse, med navnet LoginDto, ser ud til at være en anden DTO (Data Transfer Object), og den ser ud til at blive brugt til at overføre login-relaterede oplysninger i en Java-applikation. Her er nogle punkter om denne klasse:
Funktion og anvendelse:

    DTO for login: Kommentaren i koden antyder, at denne klasse er designet som et Data Transfer Object specifikt til login-operationer. Det betyder, at det sandsynligvis bruges til at overføre information relateret til login-handlinger mellem forskellige komponenter i applikationen.

    Felter:
        email: Sandsynligvis bruges til at indeholde brugerens e-mailadresse ved login.
        password: Sandsynligvis bruges til at indeholde brugerens adgangskode ved login. Det er vigtigt at bemærke, at opbevaring af adgangskoder kræver normalt særlig forsigtighed, og det anbefales normalt at bruge sikre metoder som hash-funktioner og saltning.

    Lombok-annoteringer:
        @Data: Denne annotation fra Lombok genererer standardmetoder som gettere, settere, toString(), osv. Det reducerer behovet for manuel kodning af disse metoder og forbedrer kodeets læsbarhed.
        @FieldDefaults(level = AccessLevel.PRIVATE): Denne annotation fra Lombok indstiller standardadgangsniveauet til private for alle felter i klassen. Dette er en måde at sikre, at felterne ikke er offentligt tilgængelige, hvilket kan øge sikkerheden og kontrollen over klasseens tilstand.

Anvendelsesscenarier:

Denne klasse kan bruges i situationer, hvor login-oplysninger skal sendes mellem forskellige dele af en applikation. For eksempel, når en bruger indtaster deres loginoplysninger på frontend, kan disse oplysninger overføres som en instans af LoginDto til backend for yderligere behandling og validering.

Samlet set er denne klasse sandsynligvis en enkel måde at organisere og overføre login-relaterede oplysninger mellem forskellige dele af en Java-applikation.
 */
