enum class TokenType {
    NUMBER,        // Pour les nombres comme 3, 10
    STRING,        // Pour les chaînes comme "Hello, world!"
    TRUE,          // Pour la valeur booléenne true
    FALSE,         // Pour la valeur booléenne false
    NULL,          // Pour la valeur null
    IDENTIFIER,    // Pour les identifiants comme a, b
    PLUS,          // Pour l'opérateur +
    MINUS,         // Pour l'opérateur -
    STAR,          // Pour l'opérateur *
    SLASH,         // Pour l'opérateur /
    AND,           // Pour l'opérateur logique &&
    OR,            // Pour l'opérateur logique ||
    GREATER,       // Pour l'opérateur >
    GREATER_EQUAL, // Pour l'opérateur >=
    LESS,          // Pour l'opérateur <
    LESS_EQUAL,    // Pour l'opérateur <=
    EQUAL,         // Pour l'opérateur ==
    NOT_EQUAL,     // Pour l'opérateur !=
    LEFT_PAREN,    // Pour (
    RIGHT_PAREN,   // Pour )
    EOF            // Fin de fichier
}