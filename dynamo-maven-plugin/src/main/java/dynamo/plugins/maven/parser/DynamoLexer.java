package dynamo.plugins.mavenparser;

import dynamo.plugins.mavenmodel.*;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class DynamoLexer extends Lexer {
    public static final int WS=6;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__13=13;
    public static final int ANNOTATION=5;
    public static final int T__10=10;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;
    public static final int STRING=4;

    // delegates
    // delegators

    public DynamoLexer() {;} 
    public DynamoLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public DynamoLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "dynamo\\plugin\\parser\\Dynamo.g"; }

    // $ANTLR start "T__7"
    public final void mT__7() throws RecognitionException {
        try {
            int _type = T__7;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("Application"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__7"

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("Entity"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("List<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("Set<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "ANNOTATION"
    public final void mANNOTATION() throws RecognitionException {
        try {
            int _type = ANNOTATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match('['); 
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==']') ) {
                    alt1=2;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='\\')||(LA1_0>='^' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANNOTATION"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\t' && LA3_0<='\n')||LA3_0=='\r'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        int alt4=10;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                {
                mT__7(); 

                }
                break;
            case 2 :
                {
                mT__8(); 

                }
                break;
            case 3 :
                {
                mT__9(); 

                }
                break;
            case 4 :
                {
                mT__10(); 

                }
                break;
            case 5 :
                {
                mT__11(); 

                }
                break;
            case 6 :
                {
                mT__12(); 

                }
                break;
            case 7 :
                {
                mT__13(); 

                }
                break;
            case 8 :
                {
                mANNOTATION(); 

                }
                break;
            case 9 :
                {
                mSTRING(); 

                }
                break;
            case 10 :
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\1\uffff\1\11\2\uffff\2\11\1\uffff\1\11\3\uffff\13\11\1\uffff\2"+
        "\11\1\uffff\1\11\1\35\1\11\1\uffff\3\11\1\42\1\uffff";
    static final String DFA4_eofS =
        "\43\uffff";
    static final String DFA4_minS =
        "\1\11\1\160\2\uffff\1\156\1\151\1\uffff\1\145\3\uffff\1\160\1\164"+
        "\1\163\1\164\1\154\1\151\1\164\1\74\1\151\1\164\1\74\1\uffff\1\143"+
        "\1\171\1\uffff\1\141\1\60\1\164\1\uffff\1\151\1\157\1\156\1\60\1"+
        "\uffff";
    static final String DFA4_maxS =
        "\1\175\1\160\2\uffff\1\156\1\151\1\uffff\1\145\3\uffff\1\160\1"+
        "\164\1\163\1\164\1\154\1\151\1\164\1\74\1\151\1\164\1\74\1\uffff"+
        "\1\143\1\171\1\uffff\1\141\1\172\1\164\1\uffff\1\151\1\157\1\156"+
        "\1\172\1\uffff";
    static final String DFA4_acceptS =
        "\2\uffff\1\2\1\3\2\uffff\1\6\1\uffff\1\10\1\11\1\12\13\uffff\1"+
        "\7\2\uffff\1\5\3\uffff\1\4\4\uffff\1\1";
    static final String DFA4_specialS =
        "\43\uffff}>";
    static final String[] DFA4_transitionS = {
            "\2\12\2\uffff\1\12\22\uffff\1\12\17\uffff\12\11\4\uffff\1\6"+
            "\2\uffff\1\1\3\11\1\4\6\11\1\5\6\11\1\7\7\11\1\10\5\uffff\32"+
            "\11\1\2\1\uffff\1\3",
            "\1\13",
            "",
            "",
            "\1\14",
            "\1\15",
            "",
            "\1\16",
            "",
            "",
            "",
            "\1\17",
            "\1\20",
            "\1\21",
            "\1\22",
            "\1\23",
            "\1\24",
            "\1\25",
            "\1\26",
            "\1\27",
            "\1\30",
            "\1\31",
            "",
            "\1\32",
            "\1\33",
            "",
            "\1\34",
            "\12\11\7\uffff\32\11\6\uffff\32\11",
            "\1\36",
            "",
            "\1\37",
            "\1\40",
            "\1\41",
            "\12\11\7\uffff\32\11\6\uffff\32\11",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | ANNOTATION | STRING | WS );";
        }
    }
 

}