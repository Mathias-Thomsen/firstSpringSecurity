package com.example.firstspringsecurity.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto implements Serializable {

    //it's a Data Trasfer Object for registration
    String firstName ;
    String lastName ;
    String email;
    String password ;
}


/*
Denne klasse, kaldet RegisterDto, ser også ud til at være en DTO (Data Transfer Object), og den synes at være designet til at overføre registreringsrelaterede oplysninger i en Java-applikation. Her er nogle observationer om denne klasse:
Funktionalitet og anvendelse:

    DTO for registrering: Kommentaren i koden angiver, at denne klasse er designet som et Data Transfer Object specifikt til registreringshandlinger. Det tyder på, at den bruges til at overføre information relateret til registrering af brugere mellem forskellige dele af applikationen.

    Felter:
        firstName: Sandsynligvis bruges til at indeholde brugerens fornavn ved registrering.
        lastName: Sandsynligvis bruges til at indeholde brugerens efternavn ved registrering.
        email: Bruges sandsynligvis til at indeholde brugerens e-mailadresse ved registrering.
        password: Sandsynligvis bruges til at indeholde brugerens adgangskode ved registrering. Som nævnt tidligere, er det vigtigt at tage hensyn til sikkerhed ved behandling af adgangskoder.

    Lombok-annoteringer:
        @Data: Genererer standardmetoder som gettere, settere, toString(), osv., hvilket gør koden mere kompakt og letlæselig.
        @FieldDefaults(level = AccessLevel.PRIVATE): Indstiller standardadgangsniveauet til private for alle felter i klassen, hvilket øger kontrol og sikkerhed.

    Implementering af Serializable: Klassen implementerer Serializable-grænsefladen, hvilket betyder, at dens objekter kan konverteres til en sekvens af bytes. Dette kan være relevant, hvis instanser af denne klasse skal kunne gemmes eller sendes over et netværk.

Anvendelsesscenarier:

Ligesom LoginDto-klassen kan denne klasse anvendes i scenarier, hvor registreringsoplysninger skal overføres mellem forskellige komponenter i applikationen. For eksempel kan en frontend sende en instans af RegisterDto til backend, hvor oplysningerne valideres og brugeren registreres.

Samlet set tjener denne klasse sandsynligvis til at organisere og overføre registreringsrelaterede oplysninger og demonstrerer best practices for brugen af DTO'er i Java-applikationsudvikling.

 */