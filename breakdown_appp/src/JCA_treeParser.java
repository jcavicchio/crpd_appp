// $ANTLR 3.4 C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g 2017-12-02 12:20:32

import java.lang.Math;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class JCA_treeParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CLOSE_BRACKET", "CLOSE_CONDSECT", "CLOSE_CURLY_BRACE", "CLOSE_FUNCTION", "CLOSE_FUNCTION_CALL", "CLOSE_LOOP", "CLOSE_MATRIX", "CLOSE_MATRIX_ID", "CLOSE_MATRIX_ROW", "CLOSE_PARENTHESIS", "CLOSE_PROGRAM", "COMMA", "ID", "INT", "NEWLINE", "OPEN_BRACKET", "OPEN_CONDSECT", "OPEN_CURLY_BRACE", "OPEN_FUNCTION", "OPEN_FUNCTION_CALL", "OPEN_LOOP", "OPEN_MATRIX", "OPEN_MATRIX_ID", "OPEN_MATRIX_ROW", "OPEN_PARENTHESIS", "OPEN_PROGRAM", "WS"
    };

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
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public JCA_treeParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public JCA_treeParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return JCA_treeParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g"; }


    boolean displayBruteForceSolution = false;
    boolean displayProductions = false;
    boolean displayProgramSolution = true;
    boolean displayPCMProductions = false;
    boolean displayPCMMatrix = false;
    int WCETCRPD;
    JCSelectOptimalPP problem = new JCSelectOptimalPP();



    // $ANTLR start "prog"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:22:1: prog : brt q nb nm grammarfile pcm ( pcmids )? ( function )* OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE ( function2 )* OPEN_PROGRAM b= blocks2 CLOSE_PROGRAM NEWLINE ;
    public final void prog() throws RecognitionException {
        JCBlock b =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:22:6: ( brt q nb nm grammarfile pcm ( pcmids )? ( function )* OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE ( function2 )* OPEN_PROGRAM b= blocks2 CLOSE_PROGRAM NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:22:8: brt q nb nm grammarfile pcm ( pcmids )? ( function )* OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE ( function2 )* OPEN_PROGRAM b= blocks2 CLOSE_PROGRAM NEWLINE
            {
            pushFollow(FOLLOW_brt_in_prog27);
            brt();

            state._fsp--;


            pushFollow(FOLLOW_q_in_prog29);
            q();

            state._fsp--;


            pushFollow(FOLLOW_nb_in_prog31);
            nb();

            state._fsp--;


            pushFollow(FOLLOW_nm_in_prog33);
            nm();

            state._fsp--;


            pushFollow(FOLLOW_grammarfile_in_prog35);
            grammarfile();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile");
                }
                JCSelectOptimalPP.setSelectOptimalPP(problem.getSelectOptimalPPID(), problem);


            pushFollow(FOLLOW_pcm_in_prog41);
            pcm();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:39:1: ( pcmids )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==OPEN_MATRIX_ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:39:1: pcmids
                    {
                    pushFollow(FOLLOW_pcmids_in_prog46);
                    pcmids();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm pcmids?");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:47:1: ( function )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==OPEN_FUNCTION) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:47:1: function
            	    {
            	    pushFollow(FOLLOW_function_in_prog52);
            	    function();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm pcmids? function?");
                }


            match(input,OPEN_PROGRAM,FOLLOW_OPEN_PROGRAM_in_prog58); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm pcmids? function? OPEN_PROGRAM");
                }
                problem.createCostMatrices();
                problem.createBlock();
                problem.markProgramBlock();
                problem.setProgramStarted();


            pushFollow(FOLLOW_blocks_in_prog64);
            blocks();

            state._fsp--;


            match(input,CLOSE_PROGRAM,FOLLOW_CLOSE_PROGRAM_in_prog66); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_prog68); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE");
                }
                
                problem.storeBlock();
                problem.programProduction();
                
                if (displayBruteForceSolution == true)
                {
                    problem.traversePaths();
                    problem.bruteForceSolution();
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:84:1: ( function2 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==OPEN_FUNCTION) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:84:1: function2
            	    {
            	    pushFollow(FOLLOW_function2_in_prog73);
            	    function2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE function2?");
                }


            match(input,OPEN_PROGRAM,FOLLOW_OPEN_PROGRAM_in_prog79); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE function2? OPEN_PROGRAM");
                }
                problem.accessBlock();


            pushFollow(FOLLOW_blocks2_in_prog87);
            b=blocks2();

            state._fsp--;


            match(input,CLOSE_PROGRAM,FOLLOW_CLOSE_PROGRAM_in_prog89); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_prog91); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : brt q nb nm grammarfile pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE function2? OPEN_PROGRAM b=blocks2 CLOSE_PROGRAM NEWLINE");
                }
                
                if (displayProgramSolution == true)
                {
                    problem.displayProgramSolution();
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "prog"



    // $ANTLR start "grammarfile"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:116:1: grammarfile : ID ;
    public final void grammarfile() throws RecognitionException {
        Token ID1=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:116:12: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:116:14: ID
            {
            ID1=(Token)match(input,ID,FOLLOW_ID_in_grammarfile102); 


                if (displayProductions == true)
                {
                    System.out.println("Production: grammarfile: ID --> grammarfile = " + (ID1!=null?ID1.getText():null));
                }
                problem.setGrammarFileName((ID1!=null?ID1.getText():null)+"_grammar.txt");


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "grammarfile"



    // $ANTLR start "brt"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:126:1: brt : INT ;
    public final void brt() throws RecognitionException {
        Token INT2=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:126:4: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:126:6: INT
            {
            INT2=(Token)match(input,INT,FOLLOW_INT_in_brt112); 


                if (displayProductions == true)
                {
                    System.out.println("Production: brt: INT --> brt = " + (INT2!=null?INT2.getText():null));
                }
                // Block reload cycles.
                int brt = Integer.parseInt((INT2!=null?INT2.getText():null));
                problem.setBRTValue(brt);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "brt"



    // $ANTLR start "q"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:138:1: q : INT ;
    public final void q() throws RecognitionException {
        Token INT3=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:138:2: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:138:4: INT
            {
            INT3=(Token)match(input,INT,FOLLOW_INT_in_q122); 


                if (displayProductions == true)
                {
                    System.out.println("Production: q: INT --> q = " + (INT3!=null?INT3.getText():null));
                }
                // Maximum allowable non-preemptive region.
                int Q = Integer.parseInt((INT3!=null?INT3.getText():null));
                problem.setQValue(Q);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "q"



    // $ANTLR start "nb"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:150:1: nb : INT ;
    public final void nb() throws RecognitionException {
        Token INT4=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:150:3: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:150:5: INT
            {
            INT4=(Token)match(input,INT,FOLLOW_INT_in_nb132); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: nb: INT --> nb = " + (INT4!=null?INT4.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT4!=null?INT4.getText():null));
                problem.setNBValue(N);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "nb"



    // $ANTLR start "nm"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:162:1: nm : INT ;
    public final void nm() throws RecognitionException {
        Token INT5=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:162:3: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:162:5: INT
            {
            INT5=(Token)match(input,INT,FOLLOW_INT_in_nm144); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: nm: INT --> nm = " + (INT5!=null?INT5.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT5!=null?INT5.getText():null));
                problem.setNMValue(N);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "nm"



    // $ANTLR start "blocks"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:177:1: blocks returns [JCBlock bBlock] : (sblk= sb (b= blocks )? |cblk= cb (b= blocks )? |cfunctioncall= functioncall (b= blocks )? |cloop= loop (b= blocks )? );
    public final JCBlock blocks() throws RecognitionException {
        JCBlock bBlock = null;


        JCSectionBlock sblk =null;

        JCBlock b =null;

        JCConditionalBlock cblk =null;

        JCFunctionCallBlock cfunctioncall =null;

        JCLoopBlock cloop =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:177:33: (sblk= sb (b= blocks )? |cblk= cb (b= blocks )? |cfunctioncall= functioncall (b= blocks )? |cloop= loop (b= blocks )? )
            int alt8=4;
            switch ( input.LA(1) ) {
            case OPEN_BRACKET:
                {
                alt8=1;
                }
                break;
            case OPEN_CURLY_BRACE:
                {
                alt8=2;
                }
                break;
            case OPEN_FUNCTION_CALL:
                {
                alt8=3;
                }
                break;
            case OPEN_LOOP:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }

            switch (alt8) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:179:5: sblk= sb (b= blocks )?
                    {
                    pushFollow(FOLLOW_sb_in_blocks168);
                    sblk=sb();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: sblk=sb");
                        }
                        bBlock = problem.addSectionToBlocks(sblk);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:188:2: (b= blocks )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==OPEN_BRACKET||LA4_0==OPEN_CURLY_BRACE||(LA4_0 >= OPEN_FUNCTION_CALL && LA4_0 <= OPEN_LOOP)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:188:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks177);
                            b=blocks();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: sblk=sb (b=blocks)?");
                        }


                    }
                    break;
                case 2 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:198:5: cblk= cb (b= blocks )?
                    {
                    pushFollow(FOLLOW_cb_in_blocks191);
                    cblk=cb();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cblk=cb");
                        }
                        bBlock = problem.addConditionalToBlocks(cblk);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:207:2: (b= blocks )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==OPEN_BRACKET||LA5_0==OPEN_CURLY_BRACE||(LA5_0 >= OPEN_FUNCTION_CALL && LA5_0 <= OPEN_LOOP)) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:207:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks199);
                            b=blocks();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cblk=cb (b=blocks)?");
                        }


                    }
                    break;
                case 3 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:217:14: cfunctioncall= functioncall (b= blocks )?
                    {
                    pushFollow(FOLLOW_functioncall_in_blocks211);
                    cfunctioncall=functioncall();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cfunctioncall=functioncall");
                        }
                        bBlock = problem.addFunctionCallBlockToBlocks(cfunctioncall);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:226:2: (b= blocks )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==OPEN_BRACKET||LA6_0==OPEN_CURLY_BRACE||(LA6_0 >= OPEN_FUNCTION_CALL && LA6_0 <= OPEN_LOOP)) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:226:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks220);
                            b=blocks();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cfunctioncall=functioncall (b=blocks)?");
                        }


                    }
                    break;
                case 4 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:236:6: cloop= loop (b= blocks )?
                    {
                    pushFollow(FOLLOW_loop_in_blocks233);
                    cloop=loop();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cloop=loop");
                        }
                        bBlock = problem.addLoopToBlocks(cloop);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:245:2: (b= blocks )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==OPEN_BRACKET||LA7_0==OPEN_CURLY_BRACE||(LA7_0 >= OPEN_FUNCTION_CALL && LA7_0 <= OPEN_LOOP)) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:245:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks242);
                            b=blocks();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cloop=loop (b=blocks)?");
                        }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return bBlock;
    }
    // $ANTLR end "blocks"



    // $ANTLR start "cb"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:258:1: cb returns [JCConditionalBlock conditionalBlock] : OPEN_CURLY_BRACE cib= cblocks CLOSE_CURLY_BRACE ;
    public final JCConditionalBlock cb() throws RecognitionException {
        JCConditionalBlock conditionalBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:258:50: ( OPEN_CURLY_BRACE cib= cblocks CLOSE_CURLY_BRACE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:260:1: OPEN_CURLY_BRACE cib= cblocks CLOSE_CURLY_BRACE
            {
            match(input,OPEN_CURLY_BRACE,FOLLOW_OPEN_CURLY_BRACE_in_cb268); 


                if (displayProductions == true)
                {
                    System.out.println("Production: cb: { ");
                }
                conditionalBlock = problem.createConditionalBlock();


            pushFollow(FOLLOW_cblocks_in_cb276);
            cblocks();

            state._fsp--;


            match(input,CLOSE_CURLY_BRACE,FOLLOW_CLOSE_CURLY_BRACE_in_cb278); 


                if (displayProductions == true)
                {
                    System.out.println("Production: cb: { (cib=cblocks) }");
                }
                conditionalBlock = problem.storeConditionalBlock();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return conditionalBlock;
    }
    // $ANTLR end "cb"



    // $ANTLR start "lb"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:283:1: lb : lbb= bb (rb= lb )? ;
    public final void lb() throws RecognitionException {
        JCBasicBlock lbb =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:283:4: (lbb= bb (rb= lb )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:283:6: lbb= bb (rb= lb )?
            {
            pushFollow(FOLLOW_bb_in_lb295);
            lbb=bb();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: lb: lbb=bb");
                }
                problem.addBasicBlockToLinearSection(lbb);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:292:2: (rb= lb )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==OPEN_PARENTHESIS) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:292:2: rb= lb
                    {
                    pushFollow(FOLLOW_lb_in_lb303);
                    lb();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: lb: (rb=lb)?");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "lb"



    // $ANTLR start "sb"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:304:1: sb returns [JCSectionBlock sectionBlock] : OPEN_BRACKET lblk= lb CLOSE_BRACKET ;
    public final JCSectionBlock sb() throws RecognitionException {
        JCSectionBlock sectionBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:304:42: ( OPEN_BRACKET lblk= lb CLOSE_BRACKET )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:305:1: OPEN_BRACKET lblk= lb CLOSE_BRACKET
            {
            match(input,OPEN_BRACKET,FOLLOW_OPEN_BRACKET_in_sb324); 

            pushFollow(FOLLOW_lb_in_sb328);
            lb();

            state._fsp--;


            match(input,CLOSE_BRACKET,FOLLOW_CLOSE_BRACKET_in_sb330); 


                if (displayProductions == true)
                {
                    System.out.println("Production: sb: [ lblk=lb ]");
                }
                sectionBlock = problem.storeLinearSection();
                //problem.computeSectionCosts(sectionBlock);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return sectionBlock;
    }
    // $ANTLR end "sb"



    // $ANTLR start "csb"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:320:1: csb returns [JCConditionalSection conditionalSection] : OPEN_CONDSECT csblks= blocks CLOSE_CONDSECT ;
    public final JCConditionalSection csb() throws RecognitionException {
        JCConditionalSection conditionalSection = null;


        JCBlock csblks =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:320:55: ( OPEN_CONDSECT csblks= blocks CLOSE_CONDSECT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:320:57: OPEN_CONDSECT csblks= blocks CLOSE_CONDSECT
            {
            match(input,OPEN_CONDSECT,FOLLOW_OPEN_CONDSECT_in_csb349); 


                if (displayProductions == true)
                {
                    System.out.println("Production: csb: < ");
                }
                conditionalSection = problem.createConditionalSection();


            pushFollow(FOLLOW_blocks_in_csb357);
            csblks=blocks();

            state._fsp--;


            match(input,CLOSE_CONDSECT,FOLLOW_CLOSE_CONDSECT_in_csb359); 


                if (displayProductions == true)
                {
                    System.out.println("Production: csb: < csblks=blocks >");
                }
                conditionalSection = problem.storeConditionalSection();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return conditionalSection;
    }
    // $ANTLR end "csb"



    // $ANTLR start "cblocks"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:343:1: cblocks : csblks= csb (csblk= cblocks )? ;
    public final void cblocks() throws RecognitionException {
        JCConditionalSection csblks =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:343:9: (csblks= csb (csblk= cblocks )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:345:7: csblks= csb (csblk= cblocks )?
            {
            pushFollow(FOLLOW_csb_in_cblocks377);
            csblks=csb();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: cblocks: csblks=csb");
                }
                problem.addConditionalSectionToConditional(csblks);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:354:2: (csblk= cblocks )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==OPEN_CONDSECT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:354:2: csblk= cblocks
                    {
                    pushFollow(FOLLOW_cblocks_in_cblocks386);
                    cblocks();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: cblocks: csb (csblk=cblocks)");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "cblocks"



    // $ANTLR start "function"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:367:1: function returns [JCFunctionBlock function] : OPEN_FUNCTION bl= blocks functionname CLOSE_FUNCTION ;
    public final JCFunctionBlock function() throws RecognitionException {
        JCFunctionBlock function = null;


        JCBlock bl =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:367:45: ( OPEN_FUNCTION bl= blocks functionname CLOSE_FUNCTION )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:367:47: OPEN_FUNCTION bl= blocks functionname CLOSE_FUNCTION
            {
            match(input,OPEN_FUNCTION,FOLLOW_OPEN_FUNCTION_in_function408); 


                if (displayProductions == true)
                {
                    System.out.println("Production: function: #? ");
                }
                function = problem.createFunctionBlock();
                bl = problem.createBlock();


            pushFollow(FOLLOW_blocks_in_function415);
            bl=blocks();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: function: #? bl=blocks");
                }
                
                bl = problem.storeBlock();
                problem.addBlocksToFunctionBlock(bl);


            pushFollow(FOLLOW_functionname_in_function421);
            functionname();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: function: #? bl=blocks functionname");
                }


            match(input,CLOSE_FUNCTION,FOLLOW_CLOSE_FUNCTION_in_function426); 


                if (displayProductions == true)
                {
                    System.out.println("Production: function: #? bl=blocks functionname ?#");
                }
                
                function = problem.storeFunctionBlock();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return function;
    }
    // $ANTLR end "function"



    // $ANTLR start "functionname"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:408:1: functionname : ID ;
    public final void functionname() throws RecognitionException {
        Token ID6=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:408:14: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:408:16: ID
            {
            ID6=(Token)match(input,ID,FOLLOW_ID_in_functionname438); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functionname: ID --> functionname = " + (ID6!=null?ID6.getText():null));
                }
                problem.setFunctionName((ID6!=null?ID6.getText():null));


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "functionname"



    // $ANTLR start "functioncall"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:421:1: functioncall returns [JCFunctionCallBlock functioncall] : OPEN_FUNCTION_CALL bl= blocks functioncallname CLOSE_FUNCTION_CALL ;
    public final JCFunctionCallBlock functioncall() throws RecognitionException {
        JCFunctionCallBlock functioncall = null;


        JCBlock bl =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:421:57: ( OPEN_FUNCTION_CALL bl= blocks functioncallname CLOSE_FUNCTION_CALL )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:421:59: OPEN_FUNCTION_CALL bl= blocks functioncallname CLOSE_FUNCTION_CALL
            {
            match(input,OPEN_FUNCTION_CALL,FOLLOW_OPEN_FUNCTION_CALL_in_functioncall458); 


                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall: #! ");
                }
                functioncall = problem.createFunctionCallBlock();
                bl = problem.createBlock();


            pushFollow(FOLLOW_blocks_in_functioncall465);
            bl=blocks();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall: #! bl=blocks");
                }
                
                bl = problem.storeBlock();
                problem.addBlocksToFunctionCallBlock(bl);


            pushFollow(FOLLOW_functioncallname_in_functioncall471);
            functioncallname();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall: #! bl=blocks functioncallname");
                }


            match(input,CLOSE_FUNCTION_CALL,FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall476); 


                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall: #! bl=blocks functioncallname !#");
                }
                
                functioncall = problem.storeFunctionCallBlock();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return functioncall;
    }
    // $ANTLR end "functioncall"



    // $ANTLR start "functioncallname"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:462:1: functioncallname : ID ;
    public final void functioncallname() throws RecognitionException {
        Token ID7=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:462:18: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:462:20: ID
            {
            ID7=(Token)match(input,ID,FOLLOW_ID_in_functioncallname488); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncallname: ID --> functioncallname = " + (ID7!=null?ID7.getText():null));
                }
                problem.setFunctionCallName((ID7!=null?ID7.getText():null));


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "functioncallname"



    // $ANTLR start "loop"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:475:1: loop returns [JCLoopBlock loop] : OPEN_LOOP bl= blocks maxiterations CLOSE_LOOP ;
    public final JCLoopBlock loop() throws RecognitionException {
        JCLoopBlock loop = null;


        JCBlock bl =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:475:33: ( OPEN_LOOP bl= blocks maxiterations CLOSE_LOOP )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:475:35: OPEN_LOOP bl= blocks maxiterations CLOSE_LOOP
            {
            match(input,OPEN_LOOP,FOLLOW_OPEN_LOOP_in_loop508); 


                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& ");
                }
                loop = problem.createLoopBlock();


            pushFollow(FOLLOW_blocks_in_loop515);
            bl=blocks();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& bl=blocks");
                }
                problem.addBlocksToLoop(bl);


            pushFollow(FOLLOW_maxiterations_in_loop521);
            maxiterations();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& bl=blocks maxiterations");
                }


            match(input,CLOSE_LOOP,FOLLOW_CLOSE_LOOP_in_loop526); 


                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& bl=blocks maxiterations &#");
                }
                loop = problem.storeLoopBlock();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return loop;
    }
    // $ANTLR end "loop"



    // $ANTLR start "maxiterations"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:512:1: maxiterations : INT ;
    public final void maxiterations() throws RecognitionException {
        Token INT8=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:512:15: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:512:17: INT
            {
            INT8=(Token)match(input,INT,FOLLOW_INT_in_maxiterations538); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: maxiterations: INT --> maxiterations = " + (INT8!=null?INT8.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT8!=null?INT8.getText():null));
                problem.setLoopMaxIterations(N);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "maxiterations"



    // $ANTLR start "bb"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:526:1: bb returns [JCBasicBlock basicBlock] : OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS ;
    public final JCBasicBlock bb() throws RecognitionException {
        JCBasicBlock basicBlock = null;


        Token ID9=null;
        Token INT10=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:526:38: ( OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:528:1: OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS
            {
            match(input,OPEN_PARENTHESIS,FOLLOW_OPEN_PARENTHESIS_in_bb559); 

            ID9=(Token)match(input,ID,FOLLOW_ID_in_bb561); 

            match(input,COMMA,FOLLOW_COMMA_in_bb563); 

            INT10=(Token)match(input,INT,FOLLOW_INT_in_bb565); 

            match(input,CLOSE_PARENTHESIS,FOLLOW_CLOSE_PARENTHESIS_in_bb567); 


                if (displayProductions == true)
                {
                    System.out.println("Production: bb : (ID,INT) = (" + (ID9!=null?ID9.getText():null) + "," + (INT10!=null?INT10.getText():null) + ")");
                }
                int bbWCET = Integer.parseInt((INT10!=null?INT10.getText():null));
                basicBlock = problem.createBasicBlock((ID9!=null?ID9.getText():null), bbWCET);		


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return basicBlock;
    }
    // $ANTLR end "bb"



    // $ANTLR start "blocks2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:542:1: blocks2 returns [JCBlock bBlock] : (sblk2= sb2 (b= blocks2 )? |cblk2= cb2 (b= blocks2 )? |cfunctioncall2= functioncall2 (b= blocks2 )? |cloop2= loop2 (b= blocks2 )? );
    public final JCBlock blocks2() throws RecognitionException {
        JCBlock bBlock = null;


        JCSectionBlock sblk2 =null;

        JCBlock b =null;

        JCConditionalBlock cblk2 =null;

        JCFunctionCallBlock cfunctioncall2 =null;

        JCLoopBlock cloop2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:542:34: (sblk2= sb2 (b= blocks2 )? |cblk2= cb2 (b= blocks2 )? |cfunctioncall2= functioncall2 (b= blocks2 )? |cloop2= loop2 (b= blocks2 )? )
            int alt15=4;
            switch ( input.LA(1) ) {
            case OPEN_BRACKET:
                {
                alt15=1;
                }
                break;
            case OPEN_CURLY_BRACE:
                {
                alt15=2;
                }
                break;
            case OPEN_FUNCTION_CALL:
                {
                alt15=3;
                }
                break;
            case OPEN_LOOP:
                {
                alt15=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }

            switch (alt15) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:544:6: sblk2= sb2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_sb2_in_blocks2601);
                    sblk2=sb2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: sblk2=sb2");
                        }
                        bBlock = problem.processConditionalSectionBlock(sblk2);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:553:2: (b= blocks2 )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==OPEN_BRACKET||LA11_0==OPEN_CURLY_BRACE||(LA11_0 >= OPEN_FUNCTION_CALL && LA11_0 <= OPEN_LOOP)) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:553:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2611);
                            b=blocks2();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: sblk2=sb2 (b=blocks2)?");
                        }


                    }
                    break;
                case 2 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:563:6: cblk2= cb2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_cb2_in_blocks2624);
                    cblk2=cb2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cblk2=cb2");
                        }


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:571:2: (b= blocks2 )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==OPEN_BRACKET||LA12_0==OPEN_CURLY_BRACE||(LA12_0 >= OPEN_FUNCTION_CALL && LA12_0 <= OPEN_LOOP)) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:571:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2633);
                            b=blocks2();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cblk2=cb2 (b=blocks2)");
                        }


                    }
                    break;
                case 3 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:581:15: cfunctioncall2= functioncall2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_functioncall2_in_blocks2647);
                    cfunctioncall2=functioncall2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cfunctioncall2=functioncall2");
                        }
                        bBlock = problem.processFunctionCallBlock(cfunctioncall2);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:590:2: (b= blocks2 )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==OPEN_BRACKET||LA13_0==OPEN_CURLY_BRACE||(LA13_0 >= OPEN_FUNCTION_CALL && LA13_0 <= OPEN_LOOP)) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:590:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2656);
                            b=blocks2();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cfunctioncall2=functioncall2 (b=blocks2)?");
                        }


                    }
                    break;
                case 4 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:600:7: cloop2= loop2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_loop2_in_blocks2669);
                    cloop2=loop2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cloop2=loop2");
                        }
                        bBlock = problem.processLoopBlock(cloop2);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:609:2: (b= blocks2 )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==OPEN_BRACKET||LA14_0==OPEN_CURLY_BRACE||(LA14_0 >= OPEN_FUNCTION_CALL && LA14_0 <= OPEN_LOOP)) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:609:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2678);
                            b=blocks2();

                            state._fsp--;


                            }
                            break;

                    }



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cloop2=loop2 (b=blocks2)");
                        }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return bBlock;
    }
    // $ANTLR end "blocks2"



    // $ANTLR start "cb2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:622:1: cb2 returns [JCConditionalBlock conditionalBlock] : OPEN_CURLY_BRACE cib= cblocks2 CLOSE_CURLY_BRACE ;
    public final JCConditionalBlock cb2() throws RecognitionException {
        JCConditionalBlock conditionalBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:622:51: ( OPEN_CURLY_BRACE cib= cblocks2 CLOSE_CURLY_BRACE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:624:1: OPEN_CURLY_BRACE cib= cblocks2 CLOSE_CURLY_BRACE
            {
            match(input,OPEN_CURLY_BRACE,FOLLOW_OPEN_CURLY_BRACE_in_cb2704); 


                if (displayProductions == true)
                {
                    System.out.println("Production: cb2: {");
                }
                conditionalBlock = problem.accessConditionalBlock();


            pushFollow(FOLLOW_cblocks2_in_cb2711);
            cblocks2();

            state._fsp--;


            match(input,CLOSE_CURLY_BRACE,FOLLOW_CLOSE_CURLY_BRACE_in_cb2713); 


                if (displayProductions == true)
                {
                    System.out.println("Production: cb2: { (cib=cblocks2) }");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return conditionalBlock;
    }
    // $ANTLR end "cb2"



    // $ANTLR start "lb2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:646:1: lb2 : lbb2= bb2 (rb2= lb2 )? ;
    public final void lb2() throws RecognitionException {
        JCBasicBlock lbb2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:646:5: (lbb2= bb2 (rb2= lb2 )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:646:7: lbb2= bb2 (rb2= lb2 )?
            {
            pushFollow(FOLLOW_bb2_in_lb2730);
            lbb2=bb2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: lb2: lbb2=bb2");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:654:2: (rb2= lb2 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==OPEN_PARENTHESIS) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:654:2: rb2= lb2
                    {
                    pushFollow(FOLLOW_lb2_in_lb2738);
                    lb2();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: lb2: (rb2=lb2)?");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "lb2"



    // $ANTLR start "sb2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:667:1: sb2 returns [JCSectionBlock sectionBlock] : OPEN_BRACKET lblk2= lb2 CLOSE_BRACKET ;
    public final JCSectionBlock sb2() throws RecognitionException {
        JCSectionBlock sectionBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:667:43: ( OPEN_BRACKET lblk2= lb2 CLOSE_BRACKET )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:667:45: OPEN_BRACKET lblk2= lb2 CLOSE_BRACKET
            {
            match(input,OPEN_BRACKET,FOLLOW_OPEN_BRACKET_in_sb2759); 

            pushFollow(FOLLOW_lb2_in_sb2763);
            lb2();

            state._fsp--;


            match(input,CLOSE_BRACKET,FOLLOW_CLOSE_BRACKET_in_sb2765); 


                if (displayProductions == true)
                {
                    System.out.println("Production: sb2: [ lblk2=bb2 ]");
                }
                sectionBlock = problem.accessLinearSection();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return sectionBlock;
    }
    // $ANTLR end "sb2"



    // $ANTLR start "csb2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:681:1: csb2 returns [JCConditionalSection conditionalSection] : OPEN_CONDSECT csblks2= blocks2 CLOSE_CONDSECT ;
    public final JCConditionalSection csb2() throws RecognitionException {
        JCConditionalSection conditionalSection = null;


        JCBlock csblks2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:681:56: ( OPEN_CONDSECT csblks2= blocks2 CLOSE_CONDSECT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:681:58: OPEN_CONDSECT csblks2= blocks2 CLOSE_CONDSECT
            {
            match(input,OPEN_CONDSECT,FOLLOW_OPEN_CONDSECT_in_csb2784); 


                if (displayProductions == true)
                {
                    System.out.println("Production: csb2: <");
                }
                conditionalSection = problem.accessConditionalSection();


            pushFollow(FOLLOW_blocks2_in_csb2791);
            csblks2=blocks2();

            state._fsp--;


            match(input,CLOSE_CONDSECT,FOLLOW_CLOSE_CONDSECT_in_csb2793); 


                if (displayProductions == true)
                {
                    System.out.println("Production: csb2: < csblks2=cblocks2 >");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return conditionalSection;
    }
    // $ANTLR end "csb2"



    // $ANTLR start "cblocks2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:703:1: cblocks2 : csblks2= csb2 (csblk2= cblocks2 )? ;
    public final void cblocks2() throws RecognitionException {
        JCConditionalSection csblks2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:703:10: (csblks2= csb2 (csblk2= cblocks2 )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:705:8: csblks2= csb2 (csblk2= cblocks2 )?
            {
            pushFollow(FOLLOW_csb2_in_cblocks2811);
            csblks2=csb2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: cblocks2: csblks2=csb2");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:713:2: (csblk2= cblocks2 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==OPEN_CONDSECT) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:713:2: csblk2= cblocks2
                    {
                    pushFollow(FOLLOW_cblocks2_in_cblocks2820);
                    cblocks2();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: cblocks2: csb2 (csblk2=cblocks2)");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "cblocks2"



    // $ANTLR start "function2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:726:1: function2 returns [JCFunctionBlock function2] : OPEN_FUNCTION bl2= blocks2 functionname2 CLOSE_FUNCTION ;
    public final JCFunctionBlock function2() throws RecognitionException {
        JCFunctionBlock function2 = null;


        JCBlock bl2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:726:47: ( OPEN_FUNCTION bl2= blocks2 functionname2 CLOSE_FUNCTION )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:726:49: OPEN_FUNCTION bl2= blocks2 functionname2 CLOSE_FUNCTION
            {
            match(input,OPEN_FUNCTION,FOLLOW_OPEN_FUNCTION_in_function2842); 


                if (displayProductions == true)
                {
                    System.out.println("Production: function2: #? ");
                }
                
                function2 = problem.accessFunctionBlock();
                bl2 = problem.accessBlock();


            pushFollow(FOLLOW_blocks2_in_function2849);
            bl2=blocks2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: function2: #? bl2=blocks2");
                }


            pushFollow(FOLLOW_functionname2_in_function2855);
            functionname2();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: function2: #? bl2=blocks2 functionname2");
                }


            match(input,CLOSE_FUNCTION,FOLLOW_CLOSE_FUNCTION_in_function2860); 


                if (displayProductions == true)
                {
                    System.out.println("Production: function2: #? bl2=blocks2 functionname2 ?#");
                }
                problem.computeFunctionBlockSolution(function2);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return function2;
    }
    // $ANTLR end "function2"



    // $ANTLR start "functionname2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:764:1: functionname2 : ID ;
    public final void functionname2() throws RecognitionException {
        Token ID11=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:764:15: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:764:17: ID
            {
            ID11=(Token)match(input,ID,FOLLOW_ID_in_functionname2872); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functionname2: ID --> functionname2 = " + (ID11!=null?ID11.getText():null));
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "functionname2"



    // $ANTLR start "functioncall2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:776:1: functioncall2 returns [JCFunctionCallBlock functioncall2] : OPEN_FUNCTION_CALL bl2= blocks2 functioncallname2 CLOSE_FUNCTION_CALL ;
    public final JCFunctionCallBlock functioncall2() throws RecognitionException {
        JCFunctionCallBlock functioncall2 = null;


        JCBlock bl2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:776:59: ( OPEN_FUNCTION_CALL bl2= blocks2 functioncallname2 CLOSE_FUNCTION_CALL )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:776:61: OPEN_FUNCTION_CALL bl2= blocks2 functioncallname2 CLOSE_FUNCTION_CALL
            {
            match(input,OPEN_FUNCTION_CALL,FOLLOW_OPEN_FUNCTION_CALL_in_functioncall2892); 


                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall2: #! ");
                }
                
                functioncall2 = problem.accessFunctionCallBlock();
                bl2 = problem.accessBlock();


            pushFollow(FOLLOW_blocks2_in_functioncall2899);
            bl2=blocks2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall2: #! bl2=blocks2");
                }


            pushFollow(FOLLOW_functioncallname2_in_functioncall2905);
            functioncallname2();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall2: #! bl2=blocks2 functioncallname2");
                }


            match(input,CLOSE_FUNCTION_CALL,FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall2910); 


                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall2: #! bl2=blocks2 functioncallname2 !#");
                }
                problem.computeFunctionCallBlockSolution(functioncall2);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return functioncall2;
    }
    // $ANTLR end "functioncall2"



    // $ANTLR start "functioncallname2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:814:1: functioncallname2 : ID ;
    public final void functioncallname2() throws RecognitionException {
        Token ID12=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:814:19: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:814:21: ID
            {
            ID12=(Token)match(input,ID,FOLLOW_ID_in_functioncallname2922); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncallname2: ID --> functioncallname2 = " + (ID12!=null?ID12.getText():null));
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "functioncallname2"



    // $ANTLR start "loop2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:826:1: loop2 returns [JCLoopBlock loop2] : OPEN_LOOP bl2= blocks2 maxiterations2 CLOSE_LOOP ;
    public final JCLoopBlock loop2() throws RecognitionException {
        JCLoopBlock loop2 = null;


        JCBlock bl2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:826:35: ( OPEN_LOOP bl2= blocks2 maxiterations2 CLOSE_LOOP )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:826:37: OPEN_LOOP bl2= blocks2 maxiterations2 CLOSE_LOOP
            {
            match(input,OPEN_LOOP,FOLLOW_OPEN_LOOP_in_loop2942); 


                if (displayProductions == true)
                {
                    System.out.println("Production: loop2: #& ");
                }
                loop2 = problem.accessLoopBlock();


            pushFollow(FOLLOW_blocks2_in_loop2949);
            bl2=blocks2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& bl2=blocks2");
                }


            pushFollow(FOLLOW_maxiterations2_in_loop2955);
            maxiterations2();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: loop2: #& bl2=blocks2 maxiterations2");
                }


            match(input,CLOSE_LOOP,FOLLOW_CLOSE_LOOP_in_loop2960); 


                if (displayProductions == true)
                {
                    System.out.println("Production: loop2: #& bl2=blocks2 maxiterations2 &#");
                }
                problem.computeLoopBlockSolution(loop2);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return loop2;
    }
    // $ANTLR end "loop2"



    // $ANTLR start "maxiterations2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:862:1: maxiterations2 : INT ;
    public final void maxiterations2() throws RecognitionException {
        Token INT13=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:862:16: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:862:18: INT
            {
            INT13=(Token)match(input,INT,FOLLOW_INT_in_maxiterations2972); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: maxiterations2: INT --> maxiterations2 = " + (INT13!=null?INT13.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT13!=null?INT13.getText():null));


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "maxiterations2"



    // $ANTLR start "bb2"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:876:1: bb2 returns [JCBasicBlock basicBlock] : OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS ;
    public final JCBasicBlock bb2() throws RecognitionException {
        JCBasicBlock basicBlock = null;


        Token ID14=null;
        Token INT15=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:876:39: ( OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:878:1: OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS
            {
            match(input,OPEN_PARENTHESIS,FOLLOW_OPEN_PARENTHESIS_in_bb2994); 

            ID14=(Token)match(input,ID,FOLLOW_ID_in_bb2996); 

            match(input,COMMA,FOLLOW_COMMA_in_bb2998); 

            INT15=(Token)match(input,INT,FOLLOW_INT_in_bb21000); 

            match(input,CLOSE_PARENTHESIS,FOLLOW_CLOSE_PARENTHESIS_in_bb21002); 


                if (displayProductions == true)
                {
                    System.out.println("Production: bb2 : (ID,INT) = (" + (ID14!=null?ID14.getText():null) + "," + (INT15!=null?INT15.getText():null) + ")");
                }
                int bbWCET = Integer.parseInt((INT15!=null?INT15.getText():null));
                basicBlock = problem.accessBasicBlock((ID14!=null?ID14.getText():null), bbWCET);		


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return basicBlock;
    }
    // $ANTLR end "bb2"



    // $ANTLR start "pcm"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:889:1: pcm : OPEN_MATRIX pcmrows CLOSE_MATRIX NEWLINE ;
    public final void pcm() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:889:5: ( OPEN_MATRIX pcmrows CLOSE_MATRIX NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:889:7: OPEN_MATRIX pcmrows CLOSE_MATRIX NEWLINE
            {
            match(input,OPEN_MATRIX,FOLLOW_OPEN_MATRIX_in_pcm1022); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcm : OPEN_MATRIX");
                }
                problem.startPreemptionCostMatrix();


            pushFollow(FOLLOW_pcmrows_in_pcm1031);
            pcmrows();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcm : OPEN_MATRIX pcmrows");
                }


            match(input,CLOSE_MATRIX,FOLLOW_CLOSE_MATRIX_in_pcm1039); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_pcm1041); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcm : OPEN_MATRIX pcmrows pcmids? CLOSE_MATRIX");
                }
                if (displayPCMMatrix == true)
                {
                    problem.displayPreemptionCostMatrix();
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "pcm"



    // $ANTLR start "pcmrows"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:920:1: pcmrows : pcmrow ( pcmrows )? ;
    public final void pcmrows() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:920:9: ( pcmrow ( pcmrows )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:922:1: pcmrow ( pcmrows )?
            {
            pushFollow(FOLLOW_pcmrow_in_pcmrows1057);
            pcmrow();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : pcmrow");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:930:1: ( pcmrows )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==OPEN_MATRIX_ROW) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:930:1: pcmrows
                    {
                    pushFollow(FOLLOW_pcmrows_in_pcmrows1062);
                    pcmrows();

                    state._fsp--;


                    }
                    break;

            }



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : pcmrow pcmrows?");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "pcmrows"



    // $ANTLR start "pcmrow"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:940:1: pcmrow : OPEN_MATRIX_ROW pcmvalues CLOSE_MATRIX_ROW NEWLINE ;
    public final void pcmrow() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:940:8: ( OPEN_MATRIX_ROW pcmvalues CLOSE_MATRIX_ROW NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:940:10: OPEN_MATRIX_ROW pcmvalues CLOSE_MATRIX_ROW NEWLINE
            {
            match(input,OPEN_MATRIX_ROW,FOLLOW_OPEN_MATRIX_ROW_in_pcmrow1075); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : OPEN_MATRIX_ROW");
                }


            pushFollow(FOLLOW_pcmvalues_in_pcmrow1080);
            pcmvalues();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : OPEN_MATRIX_ROW pcmvalues");
                }


            match(input,CLOSE_MATRIX_ROW,FOLLOW_CLOSE_MATRIX_ROW_in_pcmrow1085); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_pcmrow1087); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : OPEN_MATRIX_ROW pcmvalues CLOSE_MATRIX_ROW NEWLINE");
                }
                problem.nextPreemptionCostMatrixRow();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "pcmrow"



    // $ANTLR start "pcmvalues"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:967:1: pcmvalues : INT ( pcmvalues )? ;
    public final void pcmvalues() throws RecognitionException {
        Token INT16=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:967:11: ( INT ( pcmvalues )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:969:1: INT ( pcmvalues )?
            {
            INT16=(Token)match(input,INT,FOLLOW_INT_in_pcmvalues1100); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmvalues : INT");
                    System.out.println("Preemption cost: " + (INT16!=null?INT16.getText():null));
                }
                int cost = Integer.parseInt((INT16!=null?INT16.getText():null));
                problem.storePreemptionCostValue(cost);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:980:1: ( pcmvalues )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==INT) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:980:1: pcmvalues
                    {
                    pushFollow(FOLLOW_pcmvalues_in_pcmvalues1105);
                    pcmvalues();

                    state._fsp--;


                    }
                    break;

            }



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmvalues : INT pcmvalues?");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "pcmvalues"



    // $ANTLR start "pcmids"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:990:1: pcmids : OPEN_MATRIX_ID pcmidvalues CLOSE_MATRIX_ID NEWLINE ;
    public final void pcmids() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:990:8: ( OPEN_MATRIX_ID pcmidvalues CLOSE_MATRIX_ID NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:990:10: OPEN_MATRIX_ID pcmidvalues CLOSE_MATRIX_ID NEWLINE
            {
            match(input,OPEN_MATRIX_ID,FOLLOW_OPEN_MATRIX_ID_in_pcmids1118); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmids : OPEN_MATRIX_ID");
                }


            pushFollow(FOLLOW_pcmidvalues_in_pcmids1123);
            pcmidvalues();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production:pcmids : OPEN_MATRIX_ID pcmidvalues");
                }


            match(input,CLOSE_MATRIX_ID,FOLLOW_CLOSE_MATRIX_ID_in_pcmids1128); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_pcmids1130); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmids : OPEN_MATRIX_ID pcmidvalues CLOSE_MATRIX_ID NEWLINE");
                }
                problem.nextPreemptionCostMatrixRow();


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "pcmids"



    // $ANTLR start "pcmidvalues"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:1017:1: pcmidvalues : INT ( pcmidvalues )? ;
    public final void pcmidvalues() throws RecognitionException {
        Token INT17=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:1017:13: ( INT ( pcmidvalues )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:1019:1: INT ( pcmidvalues )?
            {
            INT17=(Token)match(input,INT,FOLLOW_INT_in_pcmidvalues1144); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmidvalues : INT");
                    System.out.println("Instruction ID: " + (INT17!=null?INT17.getText():null));
                }
                int id = Integer.parseInt((INT17!=null?INT17.getText():null));
                problem.storePreemptionCostID(id);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:1030:1: ( pcmidvalues )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==INT) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCA_tree.g:1030:1: pcmidvalues
                    {
                    pushFollow(FOLLOW_pcmidvalues_in_pcmidvalues1149);
                    pcmidvalues();

                    state._fsp--;


                    }
                    break;

            }



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmidvalues : INT pcmidvalues?");
                }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "pcmidvalues"

    // Delegated rules


 

    public static final BitSet FOLLOW_brt_in_prog27 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_q_in_prog29 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_nb_in_prog31 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_nm_in_prog33 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_grammarfile_in_prog35 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_pcm_in_prog41 = new BitSet(new long[]{0x0000000024400000L});
    public static final BitSet FOLLOW_pcmids_in_prog46 = new BitSet(new long[]{0x0000000020400000L});
    public static final BitSet FOLLOW_function_in_prog52 = new BitSet(new long[]{0x0000000020400000L});
    public static final BitSet FOLLOW_OPEN_PROGRAM_in_prog58 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_prog64 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_CLOSE_PROGRAM_in_prog66 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_prog68 = new BitSet(new long[]{0x0000000020400000L});
    public static final BitSet FOLLOW_function2_in_prog73 = new BitSet(new long[]{0x0000000020400000L});
    public static final BitSet FOLLOW_OPEN_PROGRAM_in_prog79 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_prog87 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_CLOSE_PROGRAM_in_prog89 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_prog91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_grammarfile102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_brt112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_q122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_nb132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_nm144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sb_in_blocks168 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cb_in_blocks191 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functioncall_in_blocks211 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_blocks233 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_BRACE_in_cb268 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_cblocks_in_cb276 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CLOSE_CURLY_BRACE_in_cb278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bb_in_lb295 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_lb_in_lb303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_BRACKET_in_sb324 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_lb_in_sb328 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_CLOSE_BRACKET_in_sb330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CONDSECT_in_csb349 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_csb357 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CLOSE_CONDSECT_in_csb359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_csb_in_cblocks377 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_cblocks_in_cblocks386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_in_function408 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_function415 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functionname_in_function421 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_in_function426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functionname438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_CALL_in_functioncall458 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_functioncall465 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functioncallname_in_functioncall471 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functioncallname488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_LOOP_in_loop508 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_loop515 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_maxiterations_in_loop521 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_CLOSE_LOOP_in_loop526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_maxiterations538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PARENTHESIS_in_bb559 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_bb561 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_COMMA_in_bb563 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_INT_in_bb565 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PARENTHESIS_in_bb567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sb2_in_blocks2601 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cb2_in_blocks2624 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functioncall2_in_blocks2647 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop2_in_blocks2669 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_BRACE_in_cb2704 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_cblocks2_in_cb2711 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CLOSE_CURLY_BRACE_in_cb2713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bb2_in_lb2730 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_lb2_in_lb2738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_BRACKET_in_sb2759 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_lb2_in_sb2763 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_CLOSE_BRACKET_in_sb2765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CONDSECT_in_csb2784 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_csb2791 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CLOSE_CONDSECT_in_csb2793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_csb2_in_cblocks2811 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_cblocks2_in_cblocks2820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_in_function2842 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_function2849 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functionname2_in_function2855 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_in_function2860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functionname2872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_CALL_in_functioncall2892 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_functioncall2899 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functioncallname2_in_functioncall2905 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall2910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functioncallname2922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_LOOP_in_loop2942 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_loop2949 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_maxiterations2_in_loop2955 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_CLOSE_LOOP_in_loop2960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_maxiterations2972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PARENTHESIS_in_bb2994 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_bb2996 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_COMMA_in_bb2998 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_INT_in_bb21000 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PARENTHESIS_in_bb21002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_MATRIX_in_pcm1022 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_pcmrows_in_pcm1031 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_CLOSE_MATRIX_in_pcm1039 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_pcm1041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pcmrow_in_pcmrows1057 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_pcmrows_in_pcmrows1062 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_MATRIX_ROW_in_pcmrow1075 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_pcmvalues_in_pcmrow1080 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CLOSE_MATRIX_ROW_in_pcmrow1085 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_pcmrow1087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_pcmvalues1100 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_pcmvalues_in_pcmvalues1105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_MATRIX_ID_in_pcmids1118 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_pcmidvalues_in_pcmids1123 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_MATRIX_ID_in_pcmids1128 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_pcmids1130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_pcmidvalues1144 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_pcmidvalues_in_pcmidvalues1149 = new BitSet(new long[]{0x0000000000000002L});

}