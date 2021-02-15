package com.food.cpg.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Kartik Gevariya
 */
public final class StringConstants {
    private StringConstants() {
        throw new IllegalStateException(ErrorConstants.INITIALIZE_CONSTANT_CLASS_ERROR);
    }

    public static final Charset UTF8 = StandardCharsets.UTF_8;

    public static final String EMPTY = "";

    public static final String COMMA = ",";
    public static final String COMMA_SP = ", ";

    public static final String SPACE = " ";

    public static final String DOT = ".";

    public static final String UNDER_SCORE = "_";

    public static final String HYPHEN = "-";

    public static final String PIPE = "|";

    public static final String COLON = ":";

    public static final String TILDE = "~";

    public static final String EQUALS_SP = " = ";

    public static final String QUESTION = " ? ";
}