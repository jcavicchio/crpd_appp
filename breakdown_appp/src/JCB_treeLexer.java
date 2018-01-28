// $ANTLR 3.4 C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g 2017-07-16 20:57:56

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class JCB_treeLexer extends Lexer {
    public static final int EOF=-1;
    public static final int CLOSE_BRACKET=4;
    public static final int CLOSE_CONDSECT=5;
    public static final int CLOSE_CURLY_BRACE=6;
    public static final int CLOSE_FUNCTION=7;
    public static final int CLOSE_FUNCTION_CALL=8;
    public static final int CLOSE_LOOP=9;
    public static final int CLOSE_MATRIX=10;
    public static final int CLOSE_MATRIX_ID=11;
    public static final int CLOSE_MATRIX_ROW=12;
    public static final int CLOSE_PARENTHESIS=13;
    public static final int CLOSE_PROGRAM=14;
    public static final int COMMA=15;
    public static final int ID=16;
    public static final int INT=17;
    public static final int NEWLINE=18;
    public static final int OPEN_BRACKET=19;
    public static final int OPEN_CONDSECT=20;
    public static final int OPEN_CURLY_BRACE=21;
    public static final int OPEN_FUNCTION=22;
    public static final int OPEN_FUNCTION_CALL=23;
    public static final int OPEN_LOOP=24;
    public static final int OPEN_MATRIX=25;
    public static final int OPEN_MATRIX_ID=26;
    public static final int OPEN_MATRIX_ROW=27;
    public static final int OPEN_PARENTHESIS=28;
    public static final int OPEN_PROGRAM=29;
    public static final int WS=30;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public JCB_treeLexer() {} 
    public JCB_treeLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public JCB_treeLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g"; }

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1018:5: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1018:7: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1018:26: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1019:5: ( ( '-' )? ( '0' .. '9' )+ )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1019:7: ( '-' )? ( '0' .. '9' )+
            {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1019:7: ( '-' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='-') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1019:8: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1019:13: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1020:9: ( ( '\\r' )? '\\n' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1020:11: ( '\\r' )? '\\n'
            {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1020:11: ( '\\r' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1020:11: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1021:4: ( ( ' ' | '\\t' )+ )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1021:6: ( ' ' | '\\t' )+
            {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1021:6: ( ' ' | '\\t' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='\t'||LA5_0==' ') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1022:7: ( ',' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1022:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "OPEN_MATRIX"
    public final void mOPEN_MATRIX() throws RecognitionException {
        try {
            int _type = OPEN_MATRIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1023:13: ( '#$' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1023:15: '#$'
            {
            match("#$"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_MATRIX"

    // $ANTLR start "CLOSE_MATRIX"
    public final void mCLOSE_MATRIX() throws RecognitionException {
        try {
            int _type = CLOSE_MATRIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1024:14: ( '$#' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1024:16: '$#'
            {
            match("$#"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_MATRIX"

    // $ANTLR start "OPEN_MATRIX_ROW"
    public final void mOPEN_MATRIX_ROW() throws RecognitionException {
        try {
            int _type = OPEN_MATRIX_ROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1025:17: ( '#@' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1025:19: '#@'
            {
            match("#@"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_MATRIX_ROW"

    // $ANTLR start "CLOSE_MATRIX_ROW"
    public final void mCLOSE_MATRIX_ROW() throws RecognitionException {
        try {
            int _type = CLOSE_MATRIX_ROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1026:18: ( '@#' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1026:20: '@#'
            {
            match("@#"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_MATRIX_ROW"

    // $ANTLR start "OPEN_MATRIX_ID"
    public final void mOPEN_MATRIX_ID() throws RecognitionException {
        try {
            int _type = OPEN_MATRIX_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1027:16: ( '#%' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1027:18: '#%'
            {
            match("#%"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_MATRIX_ID"

    // $ANTLR start "CLOSE_MATRIX_ID"
    public final void mCLOSE_MATRIX_ID() throws RecognitionException {
        try {
            int _type = CLOSE_MATRIX_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1028:17: ( '%#' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1028:19: '%#'
            {
            match("%#"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_MATRIX_ID"

    // $ANTLR start "OPEN_PROGRAM"
    public final void mOPEN_PROGRAM() throws RecognitionException {
        try {
            int _type = OPEN_PROGRAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1029:14: ( '#*' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1029:16: '#*'
            {
            match("#*"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_PROGRAM"

    // $ANTLR start "CLOSE_PROGRAM"
    public final void mCLOSE_PROGRAM() throws RecognitionException {
        try {
            int _type = CLOSE_PROGRAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1030:15: ( '*#' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1030:17: '*#'
            {
            match("*#"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_PROGRAM"

    // $ANTLR start "OPEN_FUNCTION"
    public final void mOPEN_FUNCTION() throws RecognitionException {
        try {
            int _type = OPEN_FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1031:15: ( '#?' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1031:17: '#?'
            {
            match("#?"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_FUNCTION"

    // $ANTLR start "CLOSE_FUNCTION"
    public final void mCLOSE_FUNCTION() throws RecognitionException {
        try {
            int _type = CLOSE_FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1032:16: ( '?#' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1032:18: '?#'
            {
            match("?#"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_FUNCTION"

    // $ANTLR start "OPEN_FUNCTION_CALL"
    public final void mOPEN_FUNCTION_CALL() throws RecognitionException {
        try {
            int _type = OPEN_FUNCTION_CALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1033:20: ( '#!' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1033:22: '#!'
            {
            match("#!"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_FUNCTION_CALL"

    // $ANTLR start "CLOSE_FUNCTION_CALL"
    public final void mCLOSE_FUNCTION_CALL() throws RecognitionException {
        try {
            int _type = CLOSE_FUNCTION_CALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1034:21: ( '!#' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1034:23: '!#'
            {
            match("!#"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_FUNCTION_CALL"

    // $ANTLR start "OPEN_PARENTHESIS"
    public final void mOPEN_PARENTHESIS() throws RecognitionException {
        try {
            int _type = OPEN_PARENTHESIS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1035:18: ( '\\(' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1035:20: '\\('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_PARENTHESIS"

    // $ANTLR start "CLOSE_PARENTHESIS"
    public final void mCLOSE_PARENTHESIS() throws RecognitionException {
        try {
            int _type = CLOSE_PARENTHESIS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1036:19: ( '\\)' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1036:21: '\\)'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_PARENTHESIS"

    // $ANTLR start "OPEN_CURLY_BRACE"
    public final void mOPEN_CURLY_BRACE() throws RecognitionException {
        try {
            int _type = OPEN_CURLY_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1037:18: ( '\\{' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1037:20: '\\{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_CURLY_BRACE"

    // $ANTLR start "CLOSE_CURLY_BRACE"
    public final void mCLOSE_CURLY_BRACE() throws RecognitionException {
        try {
            int _type = CLOSE_CURLY_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1038:19: ( '\\}' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1038:21: '\\}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_CURLY_BRACE"

    // $ANTLR start "OPEN_BRACKET"
    public final void mOPEN_BRACKET() throws RecognitionException {
        try {
            int _type = OPEN_BRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1039:14: ( '\\[' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1039:16: '\\['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_BRACKET"

    // $ANTLR start "CLOSE_BRACKET"
    public final void mCLOSE_BRACKET() throws RecognitionException {
        try {
            int _type = CLOSE_BRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1040:15: ( '\\]' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1040:17: '\\]'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_BRACKET"

    // $ANTLR start "OPEN_CONDSECT"
    public final void mOPEN_CONDSECT() throws RecognitionException {
        try {
            int _type = OPEN_CONDSECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1041:15: ( '<' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1041:17: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_CONDSECT"

    // $ANTLR start "CLOSE_CONDSECT"
    public final void mCLOSE_CONDSECT() throws RecognitionException {
        try {
            int _type = CLOSE_CONDSECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1042:16: ( '>' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1042:18: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_CONDSECT"

    // $ANTLR start "OPEN_LOOP"
    public final void mOPEN_LOOP() throws RecognitionException {
        try {
            int _type = OPEN_LOOP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1043:11: ( '#&' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1043:13: '#&'
            {
            match("#&"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_LOOP"

    // $ANTLR start "CLOSE_LOOP"
    public final void mCLOSE_LOOP() throws RecognitionException {
        try {
            int _type = CLOSE_LOOP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1044:12: ( '&#' )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1044:14: '&#'
            {
            match("&#"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_LOOP"

    public void mTokens() throws RecognitionException {
        // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:8: ( ID | INT | NEWLINE | WS | COMMA | OPEN_MATRIX | CLOSE_MATRIX | OPEN_MATRIX_ROW | CLOSE_MATRIX_ROW | OPEN_MATRIX_ID | CLOSE_MATRIX_ID | OPEN_PROGRAM | CLOSE_PROGRAM | OPEN_FUNCTION | CLOSE_FUNCTION | OPEN_FUNCTION_CALL | CLOSE_FUNCTION_CALL | OPEN_PARENTHESIS | CLOSE_PARENTHESIS | OPEN_CURLY_BRACE | CLOSE_CURLY_BRACE | OPEN_BRACKET | CLOSE_BRACKET | OPEN_CONDSECT | CLOSE_CONDSECT | OPEN_LOOP | CLOSE_LOOP )
        int alt6=27;
        switch ( input.LA(1) ) {
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt6=1;
            }
            break;
        case '-':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt6=2;
            }
            break;
        case '\n':
        case '\r':
            {
            alt6=3;
            }
            break;
        case '\t':
        case ' ':
            {
            alt6=4;
            }
            break;
        case ',':
            {
            alt6=5;
            }
            break;
        case '#':
            {
            switch ( input.LA(2) ) {
            case '$':
                {
                alt6=6;
                }
                break;
            case '@':
                {
                alt6=8;
                }
                break;
            case '%':
                {
                alt6=10;
                }
                break;
            case '*':
                {
                alt6=12;
                }
                break;
            case '?':
                {
                alt6=14;
                }
                break;
            case '!':
                {
                alt6=16;
                }
                break;
            case '&':
                {
                alt6=26;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 6, input);

                throw nvae;

            }

            }
            break;
        case '$':
            {
            alt6=7;
            }
            break;
        case '@':
            {
            alt6=9;
            }
            break;
        case '%':
            {
            alt6=11;
            }
            break;
        case '*':
            {
            alt6=13;
            }
            break;
        case '?':
            {
            alt6=15;
            }
            break;
        case '!':
            {
            alt6=17;
            }
            break;
        case '(':
            {
            alt6=18;
            }
            break;
        case ')':
            {
            alt6=19;
            }
            break;
        case '{':
            {
            alt6=20;
            }
            break;
        case '}':
            {
            alt6=21;
            }
            break;
        case '[':
            {
            alt6=22;
            }
            break;
        case ']':
            {
            alt6=23;
            }
            break;
        case '<':
            {
            alt6=24;
            }
            break;
        case '>':
            {
            alt6=25;
            }
            break;
        case '&':
            {
            alt6=27;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 6, 0, input);

            throw nvae;

        }

        switch (alt6) {
            case 1 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:10: ID
                {
                mID(); 


                }
                break;
            case 2 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:13: INT
                {
                mINT(); 


                }
                break;
            case 3 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:17: NEWLINE
                {
                mNEWLINE(); 


                }
                break;
            case 4 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:25: WS
                {
                mWS(); 


                }
                break;
            case 5 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:28: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 6 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:34: OPEN_MATRIX
                {
                mOPEN_MATRIX(); 


                }
                break;
            case 7 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:46: CLOSE_MATRIX
                {
                mCLOSE_MATRIX(); 


                }
                break;
            case 8 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:59: OPEN_MATRIX_ROW
                {
                mOPEN_MATRIX_ROW(); 


                }
                break;
            case 9 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:75: CLOSE_MATRIX_ROW
                {
                mCLOSE_MATRIX_ROW(); 


                }
                break;
            case 10 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:92: OPEN_MATRIX_ID
                {
                mOPEN_MATRIX_ID(); 


                }
                break;
            case 11 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:107: CLOSE_MATRIX_ID
                {
                mCLOSE_MATRIX_ID(); 


                }
                break;
            case 12 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:123: OPEN_PROGRAM
                {
                mOPEN_PROGRAM(); 


                }
                break;
            case 13 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:136: CLOSE_PROGRAM
                {
                mCLOSE_PROGRAM(); 


                }
                break;
            case 14 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:150: OPEN_FUNCTION
                {
                mOPEN_FUNCTION(); 


                }
                break;
            case 15 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:164: CLOSE_FUNCTION
                {
                mCLOSE_FUNCTION(); 


                }
                break;
            case 16 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:179: OPEN_FUNCTION_CALL
                {
                mOPEN_FUNCTION_CALL(); 


                }
                break;
            case 17 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:198: CLOSE_FUNCTION_CALL
                {
                mCLOSE_FUNCTION_CALL(); 


                }
                break;
            case 18 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:218: OPEN_PARENTHESIS
                {
                mOPEN_PARENTHESIS(); 


                }
                break;
            case 19 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:235: CLOSE_PARENTHESIS
                {
                mCLOSE_PARENTHESIS(); 


                }
                break;
            case 20 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:253: OPEN_CURLY_BRACE
                {
                mOPEN_CURLY_BRACE(); 


                }
                break;
            case 21 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:270: CLOSE_CURLY_BRACE
                {
                mCLOSE_CURLY_BRACE(); 


                }
                break;
            case 22 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:288: OPEN_BRACKET
                {
                mOPEN_BRACKET(); 


                }
                break;
            case 23 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:301: CLOSE_BRACKET
                {
                mCLOSE_BRACKET(); 


                }
                break;
            case 24 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:315: OPEN_CONDSECT
                {
                mOPEN_CONDSECT(); 


                }
                break;
            case 25 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:329: CLOSE_CONDSECT
                {
                mCLOSE_CONDSECT(); 


                }
                break;
            case 26 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:344: OPEN_LOOP
                {
                mOPEN_LOOP(); 


                }
                break;
            case 27 :
                // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1:354: CLOSE_LOOP
                {
                mCLOSE_LOOP(); 


                }
                break;

        }

    }


 

}