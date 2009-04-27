package dynamo.plugins.mavenparser;

import dynamo.plugins.mavenmodel.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class DynamoParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "STRING", "ANNOTATION", "WS", "'Application'", "'{'", "'}'", "'Entity'", "'List<'", "'>'", "'Set<'"
    };
    public static final int WS=6;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int ANNOTATION=5;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;
    public static final int STRING=4;

    // delegates
    // delegators


        public DynamoParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public DynamoParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return DynamoParser.tokenNames; }
    public String getGrammarFileName() { return "dynamo\\plugin\\parser\\Dynamo.g"; }



    // $ANTLR start "aplication"
    public final Application aplication() throws RecognitionException {
        Application app = null;

        Token appName=null;
        Entity e = null;


        try {
            {
            match(input,7,FOLLOW_7_in_aplication36); 
            appName=(Token)match(input,STRING,FOLLOW_STRING_in_aplication40); 
            match(input,8,FOLLOW_8_in_aplication42); 
             app = new Application((appName!=null?appName.getText():null)); 
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==10) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    {
            	    pushFollow(FOLLOW_entity_in_aplication51);
            	    e=entity();

            	    state._fsp--;

            	     app.addEntity(e); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            match(input,9,FOLLOW_9_in_aplication62); 
            match(input,EOF,FOLLOW_EOF_in_aplication64); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return app;
    }
    // $ANTLR end "aplication"


    // $ANTLR start "entity"
    public final Entity entity() throws RecognitionException {
        Entity e = null;

        Token eName=null;
        Token attrName=null;
        Token ANNOTATION1=null;
        DynamoParser.attributeType_return attributeType2 = null;


        try {
            {
            match(input,10,FOLLOW_10_in_entity78); 
            eName=(Token)match(input,STRING,FOLLOW_STRING_in_entity82); 
            match(input,8,FOLLOW_8_in_entity84); 
             e = new Entity((eName!=null?eName.getText():null)); 
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==ANNOTATION) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    {
            	    {
            	    ANNOTATION1=(Token)match(input,ANNOTATION,FOLLOW_ANNOTATION_in_entity94); 

            	    }

            	    pushFollow(FOLLOW_attributeType_in_entity98);
            	    attributeType2=attributeType();

            	    state._fsp--;

            	    attrName=(Token)match(input,STRING,FOLLOW_STRING_in_entity102); 
            	     e.addAttributeAnnotation((attrName!=null?attrName.getText():null), (ANNOTATION1!=null?ANNOTATION1.getText():null)); 
            	     e.addAttributeType((attrName!=null?attrName.getText():null), (attributeType2!=null?input.toString(attributeType2.start,attributeType2.stop):null)); 

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

            match(input,9,FOLLOW_9_in_entity122); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "entity"

    public static class attributeType_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "attributeType"
    public final DynamoParser.attributeType_return attributeType() throws RecognitionException {
        DynamoParser.attributeType_return retval = new DynamoParser.attributeType_return();
        retval.start = input.LT(1);

        try {
            int alt3=3;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt3=1;
                }
                break;
            case 11:
                {
                alt3=2;
                }
                break;
            case 13:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    {
                    match(input,STRING,FOLLOW_STRING_in_attributeType133); 

                    }
                    break;
                case 2 :
                    {
                    match(input,11,FOLLOW_11_in_attributeType137); 
                    match(input,STRING,FOLLOW_STRING_in_attributeType139); 
                    match(input,12,FOLLOW_12_in_attributeType141); 

                    }
                    break;
                case 3 :
                    {
                    match(input,13,FOLLOW_13_in_attributeType145); 
                    match(input,STRING,FOLLOW_STRING_in_attributeType147); 
                    match(input,12,FOLLOW_12_in_attributeType149); 

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "attributeType"

    // Delegated rules


 

    public static final BitSet FOLLOW_7_in_aplication36 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_aplication40 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_aplication42 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_entity_in_aplication51 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_9_in_aplication62 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_aplication64 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_entity78 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_entity82 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_entity84 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ANNOTATION_in_entity94 = new BitSet(new long[]{0x0000000000002810L});
    public static final BitSet FOLLOW_attributeType_in_entity98 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_entity102 = new BitSet(new long[]{0x0000000000000220L});
    public static final BitSet FOLLOW_9_in_entity122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_attributeType133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_attributeType137 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_attributeType139 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_attributeType141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_attributeType145 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_attributeType147 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_attributeType149 = new BitSet(new long[]{0x0000000000000002L});

}