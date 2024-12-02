enum class TokenType {
    // Types de tokens pour les valeurs
    NUMBER,
    STRING,
    TRUE,
    FALSE,
    NULL,

    // Types de tokens pour les identifiants
    IDENTIFIER,

    // Types de tokens pour les opérateurs arithmétiques
    PLUS,
    MINUS,
    STAR,
    SLASH,

    // Types de tokens pour les opérateurs logiques
    AND,
    OR,

    // Types de tokens pour les opérateurs de comparaison
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    EQUAL,
    NOT_EQUAL,

    // Types de tokens pour les parenthèses
    LEFT_PAREN,
    RIGHT_PAREN,

    // Token de fin de fichier
    EOF
}