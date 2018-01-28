// $ANTLR 3.4 C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g 2017-07-16 20:57:56

import java.lang.Math;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class JCB_treeParser extends Parser {
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


    public JCB_treeParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public JCB_treeParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return JCB_treeParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g"; }


    boolean displayBruteForceSolution = false;
    boolean displayProductions = false;
    boolean displayProgramSolution = true;
    boolean displayPCMProductions = false;
    boolean displayPCMMatrix = false;
    int WCETCRPD;
    JCSelectOptimalPP problem = new JCSelectOptimalPP();



    // $ANTLR start "prog"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:22:1: prog : q nb nm pcm ( pcmids )? ( function )? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE ( function2 )? OPEN_PROGRAM b= blocks2 CLOSE_PROGRAM NEWLINE ;
    public final void prog() throws RecognitionException {
        JCBlock b =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:22:6: ( q nb nm pcm ( pcmids )? ( function )? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE ( function2 )? OPEN_PROGRAM b= blocks2 CLOSE_PROGRAM NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:22:8: q nb nm pcm ( pcmids )? ( function )? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE ( function2 )? OPEN_PROGRAM b= blocks2 CLOSE_PROGRAM NEWLINE
            {
            pushFollow(FOLLOW_q_in_prog27);
            q();

            state._fsp--;


            pushFollow(FOLLOW_nb_in_prog29);
            nb();

            state._fsp--;


            pushFollow(FOLLOW_nm_in_prog31);
            nm();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n");
                }
                JCSelectOptimalPP.setSelectOptimalPP(problem.getSelectOptimalPPID(), problem);


            pushFollow(FOLLOW_pcm_in_prog37);
            pcm();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:39:1: ( pcmids )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==OPEN_MATRIX_ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:39:1: pcmids
                    {
                    pushFollow(FOLLOW_pcmids_in_prog42);
                    pcmids();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm pcmids?");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:47:1: ( function )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==OPEN_FUNCTION) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:47:1: function
                    {
                    pushFollow(FOLLOW_function_in_prog48);
                    function();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm pcmids? function?");
                }


            match(input,OPEN_PROGRAM,FOLLOW_OPEN_PROGRAM_in_prog54); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm pcmids? function? OPEN_PROGRAM");
                }
                problem.createCostMatrices();
                problem.createBlock();
                problem.markProgramBlock();
                problem.setProgramStarted();


            pushFollow(FOLLOW_blocks_in_prog60);
            blocks();

            state._fsp--;


            match(input,CLOSE_PROGRAM,FOLLOW_CLOSE_PROGRAM_in_prog62); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_prog64); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE");
                }
                
                problem.storeBlock();
                problem.programProduction();
                
                if (displayBruteForceSolution == true)
                {
                    problem.traversePaths();
                    problem.bruteForceSolution();
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:84:1: ( function2 )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==OPEN_FUNCTION) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:84:1: function2
                    {
                    pushFollow(FOLLOW_function2_in_prog69);
                    function2();

                    state._fsp--;


                    }
                    break;

            }



                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE function2?");
                }


            match(input,OPEN_PROGRAM,FOLLOW_OPEN_PROGRAM_in_prog75); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE function2? OPEN_PROGRAM");
                }
                problem.accessBlock();


            pushFollow(FOLLOW_blocks2_in_prog83);
            b=blocks2();

            state._fsp--;


            match(input,CLOSE_PROGRAM,FOLLOW_CLOSE_PROGRAM_in_prog85); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_prog87); 


                if (displayProductions == true)
                {
                    System.out.println("Production: prog : q n pcm pcmids? function? OPEN_PROGRAM blocks CLOSE_PROGRAM NEWLINE function2? OPEN_PROGRAM b=blocks2 CLOSE_PROGRAM NEWLINE");
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



    // $ANTLR start "q"
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:116:1: q : INT ;
    public final void q() throws RecognitionException {
        Token INT1=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:116:2: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:116:4: INT
            {
            INT1=(Token)match(input,INT,FOLLOW_INT_in_q98); 


                if (displayProductions == true)
                {
                    System.out.println("Production: q: INT --> q = " + (INT1!=null?INT1.getText():null));
                }
                // Maximum allowable non-preemptive region.
                int Q = Integer.parseInt((INT1!=null?INT1.getText():null));
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:128:1: nb : INT ;
    public final void nb() throws RecognitionException {
        Token INT2=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:128:3: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:128:5: INT
            {
            INT2=(Token)match(input,INT,FOLLOW_INT_in_nb108); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: nb: INT --> nb = " + (INT2!=null?INT2.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT2!=null?INT2.getText():null));
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:140:1: nm : INT ;
    public final void nm() throws RecognitionException {
        Token INT3=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:140:3: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:140:5: INT
            {
            INT3=(Token)match(input,INT,FOLLOW_INT_in_nm120); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: nm: INT --> nm = " + (INT3!=null?INT3.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT3!=null?INT3.getText():null));
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:155:1: blocks returns [JCBlock bBlock] : (sblk= sb (b= blocks )? |cblk= cb (b= blocks )? |cfunctioncall= functioncall (b= blocks )? |cloop= loop (b= blocks )? );
    public final JCBlock blocks() throws RecognitionException {
        JCBlock bBlock = null;


        JCSectionBlock sblk =null;

        JCBlock b =null;

        JCConditionalBlock cblk =null;

        JCFunctionCallBlock cfunctioncall =null;

        JCLoopBlock cloop =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:155:33: (sblk= sb (b= blocks )? |cblk= cb (b= blocks )? |cfunctioncall= functioncall (b= blocks )? |cloop= loop (b= blocks )? )
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:157:5: sblk= sb (b= blocks )?
                    {
                    pushFollow(FOLLOW_sb_in_blocks144);
                    sblk=sb();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: sblk=sb");
                        }
                        bBlock = problem.addSectionToBlocks(sblk);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:166:2: (b= blocks )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==OPEN_BRACKET||LA4_0==OPEN_CURLY_BRACE||(LA4_0 >= OPEN_FUNCTION_CALL && LA4_0 <= OPEN_LOOP)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:166:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks153);
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:176:5: cblk= cb (b= blocks )?
                    {
                    pushFollow(FOLLOW_cb_in_blocks167);
                    cblk=cb();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cblk=cb");
                        }
                        bBlock = problem.addConditionalToBlocks(cblk);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:185:2: (b= blocks )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==OPEN_BRACKET||LA5_0==OPEN_CURLY_BRACE||(LA5_0 >= OPEN_FUNCTION_CALL && LA5_0 <= OPEN_LOOP)) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:185:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks175);
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:195:14: cfunctioncall= functioncall (b= blocks )?
                    {
                    pushFollow(FOLLOW_functioncall_in_blocks187);
                    cfunctioncall=functioncall();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cfunctioncall=functioncall");
                        }
                        bBlock = problem.addFunctionCallBlockToBlocks(cfunctioncall);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:204:2: (b= blocks )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==OPEN_BRACKET||LA6_0==OPEN_CURLY_BRACE||(LA6_0 >= OPEN_FUNCTION_CALL && LA6_0 <= OPEN_LOOP)) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:204:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks196);
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:214:6: cloop= loop (b= blocks )?
                    {
                    pushFollow(FOLLOW_loop_in_blocks209);
                    cloop=loop();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks: cloop=loop");
                        }
                        bBlock = problem.addLoopToBlocks(cloop);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:223:2: (b= blocks )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==OPEN_BRACKET||LA7_0==OPEN_CURLY_BRACE||(LA7_0 >= OPEN_FUNCTION_CALL && LA7_0 <= OPEN_LOOP)) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:223:2: b= blocks
                            {
                            pushFollow(FOLLOW_blocks_in_blocks218);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:236:1: cb returns [JCConditionalBlock conditionalBlock] : OPEN_CURLY_BRACE cib= cblocks CLOSE_CURLY_BRACE ;
    public final JCConditionalBlock cb() throws RecognitionException {
        JCConditionalBlock conditionalBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:236:50: ( OPEN_CURLY_BRACE cib= cblocks CLOSE_CURLY_BRACE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:238:1: OPEN_CURLY_BRACE cib= cblocks CLOSE_CURLY_BRACE
            {
            match(input,OPEN_CURLY_BRACE,FOLLOW_OPEN_CURLY_BRACE_in_cb244); 


                if (displayProductions == true)
                {
                    System.out.println("Production: cb: { ");
                }
                conditionalBlock = problem.createConditionalBlock();


            pushFollow(FOLLOW_cblocks_in_cb252);
            cblocks();

            state._fsp--;


            match(input,CLOSE_CURLY_BRACE,FOLLOW_CLOSE_CURLY_BRACE_in_cb254); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:261:1: lb : lbb= bb (rb= lb )? ;
    public final void lb() throws RecognitionException {
        JCBasicBlock lbb =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:261:4: (lbb= bb (rb= lb )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:261:6: lbb= bb (rb= lb )?
            {
            pushFollow(FOLLOW_bb_in_lb271);
            lbb=bb();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: lb: lbb=bb");
                }
                problem.addBasicBlockToLinearSection(lbb);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:270:2: (rb= lb )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==OPEN_PARENTHESIS) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:270:2: rb= lb
                    {
                    pushFollow(FOLLOW_lb_in_lb279);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:282:1: sb returns [JCSectionBlock sectionBlock] : OPEN_BRACKET lblk= lb CLOSE_BRACKET ;
    public final JCSectionBlock sb() throws RecognitionException {
        JCSectionBlock sectionBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:282:42: ( OPEN_BRACKET lblk= lb CLOSE_BRACKET )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:283:1: OPEN_BRACKET lblk= lb CLOSE_BRACKET
            {
            match(input,OPEN_BRACKET,FOLLOW_OPEN_BRACKET_in_sb300); 

            pushFollow(FOLLOW_lb_in_sb304);
            lb();

            state._fsp--;


            match(input,CLOSE_BRACKET,FOLLOW_CLOSE_BRACKET_in_sb306); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:298:1: csb returns [JCConditionalSection conditionalSection] : OPEN_CONDSECT csblks= blocks CLOSE_CONDSECT ;
    public final JCConditionalSection csb() throws RecognitionException {
        JCConditionalSection conditionalSection = null;


        JCBlock csblks =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:298:55: ( OPEN_CONDSECT csblks= blocks CLOSE_CONDSECT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:298:57: OPEN_CONDSECT csblks= blocks CLOSE_CONDSECT
            {
            match(input,OPEN_CONDSECT,FOLLOW_OPEN_CONDSECT_in_csb325); 


                if (displayProductions == true)
                {
                    System.out.println("Production: csb: < ");
                }
                conditionalSection = problem.createConditionalSection();


            pushFollow(FOLLOW_blocks_in_csb333);
            csblks=blocks();

            state._fsp--;


            match(input,CLOSE_CONDSECT,FOLLOW_CLOSE_CONDSECT_in_csb335); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:321:1: cblocks : csblks= csb (csblk= cblocks )? ;
    public final void cblocks() throws RecognitionException {
        JCConditionalSection csblks =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:321:9: (csblks= csb (csblk= cblocks )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:323:7: csblks= csb (csblk= cblocks )?
            {
            pushFollow(FOLLOW_csb_in_cblocks353);
            csblks=csb();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: cblocks: csblks=csb");
                }
                problem.addConditionalSectionToConditional(csblks);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:332:2: (csblk= cblocks )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==OPEN_CONDSECT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:332:2: csblk= cblocks
                    {
                    pushFollow(FOLLOW_cblocks_in_cblocks362);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:345:1: function returns [JCFunctionBlock function] : OPEN_FUNCTION bl= blocks functionname CLOSE_FUNCTION ;
    public final JCFunctionBlock function() throws RecognitionException {
        JCFunctionBlock function = null;


        JCBlock bl =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:345:45: ( OPEN_FUNCTION bl= blocks functionname CLOSE_FUNCTION )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:345:47: OPEN_FUNCTION bl= blocks functionname CLOSE_FUNCTION
            {
            match(input,OPEN_FUNCTION,FOLLOW_OPEN_FUNCTION_in_function384); 


                if (displayProductions == true)
                {
                    System.out.println("Production: function: #? ");
                }
                function = problem.createFunctionBlock();
                bl = problem.createBlock();


            pushFollow(FOLLOW_blocks_in_function391);
            bl=blocks();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: function: #? bl=blocks");
                }
                
                bl = problem.storeBlock();
                problem.addBlocksToFunctionBlock(bl);


            pushFollow(FOLLOW_functionname_in_function397);
            functionname();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: function: #? bl=blocks functionname");
                }


            match(input,CLOSE_FUNCTION,FOLLOW_CLOSE_FUNCTION_in_function402); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:386:1: functionname : ID ;
    public final void functionname() throws RecognitionException {
        Token ID4=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:386:14: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:386:16: ID
            {
            ID4=(Token)match(input,ID,FOLLOW_ID_in_functionname414); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functionname: ID --> functionname = " + (ID4!=null?ID4.getText():null));
                }
                problem.setFunctionName((ID4!=null?ID4.getText():null));


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:399:1: functioncall returns [JCFunctionCallBlock functioncall] : OPEN_FUNCTION_CALL bl= blocks functioncallname CLOSE_FUNCTION_CALL ;
    public final JCFunctionCallBlock functioncall() throws RecognitionException {
        JCFunctionCallBlock functioncall = null;


        JCBlock bl =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:399:57: ( OPEN_FUNCTION_CALL bl= blocks functioncallname CLOSE_FUNCTION_CALL )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:399:59: OPEN_FUNCTION_CALL bl= blocks functioncallname CLOSE_FUNCTION_CALL
            {
            match(input,OPEN_FUNCTION_CALL,FOLLOW_OPEN_FUNCTION_CALL_in_functioncall434); 


                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall: #! ");
                }
                functioncall = problem.createFunctionCallBlock();
                bl = problem.createBlock();


            pushFollow(FOLLOW_blocks_in_functioncall441);
            bl=blocks();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall: #! bl=blocks");
                }
                
                bl = problem.storeBlock();
                problem.addBlocksToFunctionCallBlock(bl);


            pushFollow(FOLLOW_functioncallname_in_functioncall447);
            functioncallname();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall: #! bl=blocks functioncallname");
                }


            match(input,CLOSE_FUNCTION_CALL,FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall452); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:440:1: functioncallname : ID ;
    public final void functioncallname() throws RecognitionException {
        Token ID5=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:440:18: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:440:20: ID
            {
            ID5=(Token)match(input,ID,FOLLOW_ID_in_functioncallname464); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncallname: ID --> functioncallname = " + (ID5!=null?ID5.getText():null));
                }
                problem.setFunctionCallName((ID5!=null?ID5.getText():null));


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:453:1: loop returns [JCLoopBlock loop] : OPEN_LOOP bl= blocks maxiterations CLOSE_LOOP ;
    public final JCLoopBlock loop() throws RecognitionException {
        JCLoopBlock loop = null;


        JCBlock bl =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:453:33: ( OPEN_LOOP bl= blocks maxiterations CLOSE_LOOP )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:453:35: OPEN_LOOP bl= blocks maxiterations CLOSE_LOOP
            {
            match(input,OPEN_LOOP,FOLLOW_OPEN_LOOP_in_loop484); 


                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& ");
                }
                loop = problem.createLoopBlock();


            pushFollow(FOLLOW_blocks_in_loop491);
            bl=blocks();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& bl=blocks");
                }
                problem.addBlocksToLoop(bl);


            pushFollow(FOLLOW_maxiterations_in_loop497);
            maxiterations();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& bl=blocks maxiterations");
                }


            match(input,CLOSE_LOOP,FOLLOW_CLOSE_LOOP_in_loop502); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:490:1: maxiterations : INT ;
    public final void maxiterations() throws RecognitionException {
        Token INT6=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:490:15: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:490:17: INT
            {
            INT6=(Token)match(input,INT,FOLLOW_INT_in_maxiterations514); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: maxiterations: INT --> maxiterations = " + (INT6!=null?INT6.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT6!=null?INT6.getText():null));
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:504:1: bb returns [JCBasicBlock basicBlock] : OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS ;
    public final JCBasicBlock bb() throws RecognitionException {
        JCBasicBlock basicBlock = null;


        Token ID7=null;
        Token INT8=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:504:38: ( OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:506:1: OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS
            {
            match(input,OPEN_PARENTHESIS,FOLLOW_OPEN_PARENTHESIS_in_bb535); 

            ID7=(Token)match(input,ID,FOLLOW_ID_in_bb537); 

            match(input,COMMA,FOLLOW_COMMA_in_bb539); 

            INT8=(Token)match(input,INT,FOLLOW_INT_in_bb541); 

            match(input,CLOSE_PARENTHESIS,FOLLOW_CLOSE_PARENTHESIS_in_bb543); 


                if (displayProductions == true)
                {
                    System.out.println("Production: bb : (ID,INT) = (" + (ID7!=null?ID7.getText():null) + "," + (INT8!=null?INT8.getText():null) + ")");
                }
                int bbWCET = Integer.parseInt((INT8!=null?INT8.getText():null));
                basicBlock = problem.createBasicBlock((ID7!=null?ID7.getText():null), bbWCET);		


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:520:1: blocks2 returns [JCBlock bBlock] : (sblk2= sb2 (b= blocks2 )? |cblk2= cb2 (b= blocks2 )? |cfunctioncall2= functioncall2 (b= blocks2 )? |cloop2= loop2 (b= blocks2 )? );
    public final JCBlock blocks2() throws RecognitionException {
        JCBlock bBlock = null;


        JCSectionBlock sblk2 =null;

        JCBlock b =null;

        JCConditionalBlock cblk2 =null;

        JCFunctionCallBlock cfunctioncall2 =null;

        JCLoopBlock cloop2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:520:34: (sblk2= sb2 (b= blocks2 )? |cblk2= cb2 (b= blocks2 )? |cfunctioncall2= functioncall2 (b= blocks2 )? |cloop2= loop2 (b= blocks2 )? )
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:522:6: sblk2= sb2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_sb2_in_blocks2577);
                    sblk2=sb2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: sblk2=sb2");
                        }
                        bBlock = problem.processConditionalSectionBlock(sblk2);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:531:2: (b= blocks2 )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==OPEN_BRACKET||LA11_0==OPEN_CURLY_BRACE||(LA11_0 >= OPEN_FUNCTION_CALL && LA11_0 <= OPEN_LOOP)) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:531:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2587);
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:541:6: cblk2= cb2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_cb2_in_blocks2600);
                    cblk2=cb2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cblk2=cb2");
                        }


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:549:2: (b= blocks2 )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==OPEN_BRACKET||LA12_0==OPEN_CURLY_BRACE||(LA12_0 >= OPEN_FUNCTION_CALL && LA12_0 <= OPEN_LOOP)) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:549:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2609);
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:559:15: cfunctioncall2= functioncall2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_functioncall2_in_blocks2623);
                    cfunctioncall2=functioncall2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cfunctioncall2=functioncall2");
                        }
                        bBlock = problem.processFunctionCallBlock(cfunctioncall2);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:568:2: (b= blocks2 )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==OPEN_BRACKET||LA13_0==OPEN_CURLY_BRACE||(LA13_0 >= OPEN_FUNCTION_CALL && LA13_0 <= OPEN_LOOP)) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:568:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2632);
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
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:578:7: cloop2= loop2 (b= blocks2 )?
                    {
                    pushFollow(FOLLOW_loop2_in_blocks2645);
                    cloop2=loop2();

                    state._fsp--;



                        if (displayProductions == true)
                        {
                            System.out.println("Production: blocks2: cloop2=loop2");
                        }
                        bBlock = problem.processLoopBlock(cloop2);


                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:587:2: (b= blocks2 )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==OPEN_BRACKET||LA14_0==OPEN_CURLY_BRACE||(LA14_0 >= OPEN_FUNCTION_CALL && LA14_0 <= OPEN_LOOP)) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:587:2: b= blocks2
                            {
                            pushFollow(FOLLOW_blocks2_in_blocks2654);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:600:1: cb2 returns [JCConditionalBlock conditionalBlock] : OPEN_CURLY_BRACE cib= cblocks2 CLOSE_CURLY_BRACE ;
    public final JCConditionalBlock cb2() throws RecognitionException {
        JCConditionalBlock conditionalBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:600:51: ( OPEN_CURLY_BRACE cib= cblocks2 CLOSE_CURLY_BRACE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:602:1: OPEN_CURLY_BRACE cib= cblocks2 CLOSE_CURLY_BRACE
            {
            match(input,OPEN_CURLY_BRACE,FOLLOW_OPEN_CURLY_BRACE_in_cb2680); 


                if (displayProductions == true)
                {
                    System.out.println("Production: cb2: {");
                }
                conditionalBlock = problem.accessConditionalBlock();


            pushFollow(FOLLOW_cblocks2_in_cb2687);
            cblocks2();

            state._fsp--;


            match(input,CLOSE_CURLY_BRACE,FOLLOW_CLOSE_CURLY_BRACE_in_cb2689); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:624:1: lb2 : lbb2= bb2 (rb2= lb2 )? ;
    public final void lb2() throws RecognitionException {
        JCBasicBlock lbb2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:624:5: (lbb2= bb2 (rb2= lb2 )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:624:7: lbb2= bb2 (rb2= lb2 )?
            {
            pushFollow(FOLLOW_bb2_in_lb2706);
            lbb2=bb2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: lb2: lbb2=bb2");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:632:2: (rb2= lb2 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==OPEN_PARENTHESIS) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:632:2: rb2= lb2
                    {
                    pushFollow(FOLLOW_lb2_in_lb2714);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:645:1: sb2 returns [JCSectionBlock sectionBlock] : OPEN_BRACKET lblk2= lb2 CLOSE_BRACKET ;
    public final JCSectionBlock sb2() throws RecognitionException {
        JCSectionBlock sectionBlock = null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:645:43: ( OPEN_BRACKET lblk2= lb2 CLOSE_BRACKET )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:645:45: OPEN_BRACKET lblk2= lb2 CLOSE_BRACKET
            {
            match(input,OPEN_BRACKET,FOLLOW_OPEN_BRACKET_in_sb2735); 

            pushFollow(FOLLOW_lb2_in_sb2739);
            lb2();

            state._fsp--;


            match(input,CLOSE_BRACKET,FOLLOW_CLOSE_BRACKET_in_sb2741); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:659:1: csb2 returns [JCConditionalSection conditionalSection] : OPEN_CONDSECT csblks2= blocks2 CLOSE_CONDSECT ;
    public final JCConditionalSection csb2() throws RecognitionException {
        JCConditionalSection conditionalSection = null;


        JCBlock csblks2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:659:56: ( OPEN_CONDSECT csblks2= blocks2 CLOSE_CONDSECT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:659:58: OPEN_CONDSECT csblks2= blocks2 CLOSE_CONDSECT
            {
            match(input,OPEN_CONDSECT,FOLLOW_OPEN_CONDSECT_in_csb2760); 


                if (displayProductions == true)
                {
                    System.out.println("Production: csb2: <");
                }
                conditionalSection = problem.accessConditionalSection();


            pushFollow(FOLLOW_blocks2_in_csb2767);
            csblks2=blocks2();

            state._fsp--;


            match(input,CLOSE_CONDSECT,FOLLOW_CLOSE_CONDSECT_in_csb2769); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:681:1: cblocks2 : csblks2= csb2 (csblk2= cblocks2 )? ;
    public final void cblocks2() throws RecognitionException {
        JCConditionalSection csblks2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:681:10: (csblks2= csb2 (csblk2= cblocks2 )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:683:8: csblks2= csb2 (csblk2= cblocks2 )?
            {
            pushFollow(FOLLOW_csb2_in_cblocks2787);
            csblks2=csb2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: cblocks2: csblks2=csb2");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:691:2: (csblk2= cblocks2 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==OPEN_CONDSECT) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:691:2: csblk2= cblocks2
                    {
                    pushFollow(FOLLOW_cblocks2_in_cblocks2796);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:704:1: function2 returns [JCFunctionBlock function2] : OPEN_FUNCTION bl2= blocks2 functionname2 CLOSE_FUNCTION ;
    public final JCFunctionBlock function2() throws RecognitionException {
        JCFunctionBlock function2 = null;


        JCBlock bl2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:704:47: ( OPEN_FUNCTION bl2= blocks2 functionname2 CLOSE_FUNCTION )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:704:49: OPEN_FUNCTION bl2= blocks2 functionname2 CLOSE_FUNCTION
            {
            match(input,OPEN_FUNCTION,FOLLOW_OPEN_FUNCTION_in_function2818); 


                if (displayProductions == true)
                {
                    System.out.println("Production: function2: #? ");
                }
                
                function2 = problem.accessFunctionBlock();
                bl2 = problem.accessBlock();


            pushFollow(FOLLOW_blocks2_in_function2825);
            bl2=blocks2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: function2: #? bl2=blocks2");
                }


            pushFollow(FOLLOW_functionname2_in_function2831);
            functionname2();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: function2: #? bl2=blocks2 functionname2");
                }


            match(input,CLOSE_FUNCTION,FOLLOW_CLOSE_FUNCTION_in_function2836); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:742:1: functionname2 : ID ;
    public final void functionname2() throws RecognitionException {
        Token ID9=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:742:15: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:742:17: ID
            {
            ID9=(Token)match(input,ID,FOLLOW_ID_in_functionname2848); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functionname2: ID --> functionname2 = " + (ID9!=null?ID9.getText():null));
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:754:1: functioncall2 returns [JCFunctionCallBlock functioncall2] : OPEN_FUNCTION_CALL bl2= blocks2 functioncallname2 CLOSE_FUNCTION_CALL ;
    public final JCFunctionCallBlock functioncall2() throws RecognitionException {
        JCFunctionCallBlock functioncall2 = null;


        JCBlock bl2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:754:59: ( OPEN_FUNCTION_CALL bl2= blocks2 functioncallname2 CLOSE_FUNCTION_CALL )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:754:61: OPEN_FUNCTION_CALL bl2= blocks2 functioncallname2 CLOSE_FUNCTION_CALL
            {
            match(input,OPEN_FUNCTION_CALL,FOLLOW_OPEN_FUNCTION_CALL_in_functioncall2868); 


                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall2: #! ");
                }
                
                functioncall2 = problem.accessFunctionCallBlock();
                bl2 = problem.accessBlock();


            pushFollow(FOLLOW_blocks2_in_functioncall2875);
            bl2=blocks2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall2: #! bl2=blocks2");
                }


            pushFollow(FOLLOW_functioncallname2_in_functioncall2881);
            functioncallname2();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncall2: #! bl2=blocks2 functioncallname2");
                }


            match(input,CLOSE_FUNCTION_CALL,FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall2886); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:792:1: functioncallname2 : ID ;
    public final void functioncallname2() throws RecognitionException {
        Token ID10=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:792:19: ( ID )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:792:21: ID
            {
            ID10=(Token)match(input,ID,FOLLOW_ID_in_functioncallname2898); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: functioncallname2: ID --> functioncallname2 = " + (ID10!=null?ID10.getText():null));
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:804:1: loop2 returns [JCLoopBlock loop2] : OPEN_LOOP bl2= blocks2 maxiterations2 CLOSE_LOOP ;
    public final JCLoopBlock loop2() throws RecognitionException {
        JCLoopBlock loop2 = null;


        JCBlock bl2 =null;


        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:804:35: ( OPEN_LOOP bl2= blocks2 maxiterations2 CLOSE_LOOP )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:804:37: OPEN_LOOP bl2= blocks2 maxiterations2 CLOSE_LOOP
            {
            match(input,OPEN_LOOP,FOLLOW_OPEN_LOOP_in_loop2918); 


                if (displayProductions == true)
                {
                    System.out.println("Production: loop2: #& ");
                }
                loop2 = problem.accessLoopBlock();


            pushFollow(FOLLOW_blocks2_in_loop2925);
            bl2=blocks2();

            state._fsp--;



                if (displayProductions == true)
                {
                    System.out.println("Production: loop: #& bl2=blocks2");
                }


            pushFollow(FOLLOW_maxiterations2_in_loop2931);
            maxiterations2();

            state._fsp--;


             
                if (displayProductions == true)
                {
                    System.out.println("Production: loop2: #& bl2=blocks2 maxiterations2");
                }


            match(input,CLOSE_LOOP,FOLLOW_CLOSE_LOOP_in_loop2936); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:840:1: maxiterations2 : INT ;
    public final void maxiterations2() throws RecognitionException {
        Token INT11=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:840:16: ( INT )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:840:18: INT
            {
            INT11=(Token)match(input,INT,FOLLOW_INT_in_maxiterations2948); 

             
                if (displayProductions == true)
                {
                    System.out.println("Production: maxiterations2: INT --> maxiterations2 = " + (INT11!=null?INT11.getText():null));
                }
                // Number of basic blocks in the input graph.
                int N = Integer.parseInt((INT11!=null?INT11.getText():null));


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:854:1: bb2 returns [JCBasicBlock basicBlock] : OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS ;
    public final JCBasicBlock bb2() throws RecognitionException {
        JCBasicBlock basicBlock = null;


        Token ID12=null;
        Token INT13=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:854:39: ( OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:856:1: OPEN_PARENTHESIS ID COMMA INT CLOSE_PARENTHESIS
            {
            match(input,OPEN_PARENTHESIS,FOLLOW_OPEN_PARENTHESIS_in_bb2970); 

            ID12=(Token)match(input,ID,FOLLOW_ID_in_bb2972); 

            match(input,COMMA,FOLLOW_COMMA_in_bb2974); 

            INT13=(Token)match(input,INT,FOLLOW_INT_in_bb2976); 

            match(input,CLOSE_PARENTHESIS,FOLLOW_CLOSE_PARENTHESIS_in_bb2978); 


                if (displayProductions == true)
                {
                    System.out.println("Production: bb2 : (ID,INT) = (" + (ID12!=null?ID12.getText():null) + "," + (INT13!=null?INT13.getText():null) + ")");
                }
                int bbWCET = Integer.parseInt((INT13!=null?INT13.getText():null));
                basicBlock = problem.accessBasicBlock((ID12!=null?ID12.getText():null), bbWCET);		


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:867:1: pcm : OPEN_MATRIX pcmrows CLOSE_MATRIX NEWLINE ;
    public final void pcm() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:867:5: ( OPEN_MATRIX pcmrows CLOSE_MATRIX NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:867:7: OPEN_MATRIX pcmrows CLOSE_MATRIX NEWLINE
            {
            match(input,OPEN_MATRIX,FOLLOW_OPEN_MATRIX_in_pcm998); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcm : OPEN_MATRIX");
                }
                problem.startPreemptionCostMatrix();


            pushFollow(FOLLOW_pcmrows_in_pcm1007);
            pcmrows();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcm : OPEN_MATRIX pcmrows");
                }


            match(input,CLOSE_MATRIX,FOLLOW_CLOSE_MATRIX_in_pcm1015); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_pcm1017); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:898:1: pcmrows : pcmrow ( pcmrows )? ;
    public final void pcmrows() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:898:9: ( pcmrow ( pcmrows )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:900:1: pcmrow ( pcmrows )?
            {
            pushFollow(FOLLOW_pcmrow_in_pcmrows1033);
            pcmrow();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : pcmrow");
                }


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:908:1: ( pcmrows )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==OPEN_MATRIX_ROW) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:908:1: pcmrows
                    {
                    pushFollow(FOLLOW_pcmrows_in_pcmrows1038);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:918:1: pcmrow : OPEN_MATRIX_ROW pcmvalues CLOSE_MATRIX_ROW NEWLINE ;
    public final void pcmrow() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:918:8: ( OPEN_MATRIX_ROW pcmvalues CLOSE_MATRIX_ROW NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:918:10: OPEN_MATRIX_ROW pcmvalues CLOSE_MATRIX_ROW NEWLINE
            {
            match(input,OPEN_MATRIX_ROW,FOLLOW_OPEN_MATRIX_ROW_in_pcmrow1051); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : OPEN_MATRIX_ROW");
                }


            pushFollow(FOLLOW_pcmvalues_in_pcmrow1056);
            pcmvalues();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmrows : OPEN_MATRIX_ROW pcmvalues");
                }


            match(input,CLOSE_MATRIX_ROW,FOLLOW_CLOSE_MATRIX_ROW_in_pcmrow1061); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_pcmrow1063); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:945:1: pcmvalues : INT ( pcmvalues )? ;
    public final void pcmvalues() throws RecognitionException {
        Token INT14=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:945:11: ( INT ( pcmvalues )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:947:1: INT ( pcmvalues )?
            {
            INT14=(Token)match(input,INT,FOLLOW_INT_in_pcmvalues1076); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmvalues : INT");
                    System.out.println("Preemption cost: " + (INT14!=null?INT14.getText():null));
                }
                int cost = Integer.parseInt((INT14!=null?INT14.getText():null));
                problem.storePreemptionCostValue(cost);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:958:1: ( pcmvalues )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==INT) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:958:1: pcmvalues
                    {
                    pushFollow(FOLLOW_pcmvalues_in_pcmvalues1081);
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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:968:1: pcmids : OPEN_MATRIX_ID pcmidvalues CLOSE_MATRIX_ID NEWLINE ;
    public final void pcmids() throws RecognitionException {
        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:968:8: ( OPEN_MATRIX_ID pcmidvalues CLOSE_MATRIX_ID NEWLINE )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:968:10: OPEN_MATRIX_ID pcmidvalues CLOSE_MATRIX_ID NEWLINE
            {
            match(input,OPEN_MATRIX_ID,FOLLOW_OPEN_MATRIX_ID_in_pcmids1094); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmids : OPEN_MATRIX_ID");
                }


            pushFollow(FOLLOW_pcmidvalues_in_pcmids1099);
            pcmidvalues();

            state._fsp--;



                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production:pcmids : OPEN_MATRIX_ID pcmidvalues");
                }


            match(input,CLOSE_MATRIX_ID,FOLLOW_CLOSE_MATRIX_ID_in_pcmids1104); 

            match(input,NEWLINE,FOLLOW_NEWLINE_in_pcmids1106); 


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
    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:995:1: pcmidvalues : INT ( pcmidvalues )? ;
    public final void pcmidvalues() throws RecognitionException {
        Token INT15=null;

        try {
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:995:13: ( INT ( pcmidvalues )? )
            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:997:1: INT ( pcmidvalues )?
            {
            INT15=(Token)match(input,INT,FOLLOW_INT_in_pcmidvalues1120); 


                if ((displayProductions == true) && (displayPCMProductions == true))
                {
                    System.out.println("Production: pcmidvalues : INT");
                    System.out.println("Instruction ID: " + (INT15!=null?INT15.getText():null));
                }
                int id = Integer.parseInt((INT15!=null?INT15.getText():null));
                problem.storePreemptionCostID(id);


            // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1008:1: ( pcmidvalues )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==INT) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // C:\\Users\\Cavicchio\\workspace\\InterDepAdjStructure\\newgrammar\\JCB_tree.g:1008:1: pcmidvalues
                    {
                    pushFollow(FOLLOW_pcmidvalues_in_pcmidvalues1125);
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


 

    public static final BitSet FOLLOW_q_in_prog27 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_nb_in_prog29 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_nm_in_prog31 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_pcm_in_prog37 = new BitSet(new long[]{0x0000000024400000L});
    public static final BitSet FOLLOW_pcmids_in_prog42 = new BitSet(new long[]{0x0000000020400000L});
    public static final BitSet FOLLOW_function_in_prog48 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_OPEN_PROGRAM_in_prog54 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_prog60 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_CLOSE_PROGRAM_in_prog62 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_prog64 = new BitSet(new long[]{0x0000000020400000L});
    public static final BitSet FOLLOW_function2_in_prog69 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_OPEN_PROGRAM_in_prog75 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_prog83 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_CLOSE_PROGRAM_in_prog85 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_prog87 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_q98 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_nb108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_nm120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sb_in_blocks144 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cb_in_blocks167 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functioncall_in_blocks187 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_blocks209 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks_in_blocks218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_BRACE_in_cb244 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_cblocks_in_cb252 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CLOSE_CURLY_BRACE_in_cb254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bb_in_lb271 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_lb_in_lb279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_BRACKET_in_sb300 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_lb_in_sb304 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_CLOSE_BRACKET_in_sb306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CONDSECT_in_csb325 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_csb333 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CLOSE_CONDSECT_in_csb335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_csb_in_cblocks353 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_cblocks_in_cblocks362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_in_function384 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_function391 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functionname_in_function397 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_in_function402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functionname414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_CALL_in_functioncall434 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_functioncall441 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functioncallname_in_functioncall447 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functioncallname464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_LOOP_in_loop484 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks_in_loop491 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_maxiterations_in_loop497 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_CLOSE_LOOP_in_loop502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_maxiterations514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PARENTHESIS_in_bb535 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_bb537 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_COMMA_in_bb539 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_INT_in_bb541 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PARENTHESIS_in_bb543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sb2_in_blocks2577 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cb2_in_blocks2600 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functioncall2_in_blocks2623 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop2_in_blocks2645 = new BitSet(new long[]{0x0000000001A80002L});
    public static final BitSet FOLLOW_blocks2_in_blocks2654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_BRACE_in_cb2680 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_cblocks2_in_cb2687 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CLOSE_CURLY_BRACE_in_cb2689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bb2_in_lb2706 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_lb2_in_lb2714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_BRACKET_in_sb2735 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_lb2_in_sb2739 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_CLOSE_BRACKET_in_sb2741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CONDSECT_in_csb2760 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_csb2767 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CLOSE_CONDSECT_in_csb2769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_csb2_in_cblocks2787 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_cblocks2_in_cblocks2796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_in_function2818 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_function2825 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functionname2_in_function2831 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_in_function2836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functionname2848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_FUNCTION_CALL_in_functioncall2868 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_functioncall2875 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_functioncallname2_in_functioncall2881 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_CLOSE_FUNCTION_CALL_in_functioncall2886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functioncallname2898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_LOOP_in_loop2918 = new BitSet(new long[]{0x0000000001A80000L});
    public static final BitSet FOLLOW_blocks2_in_loop2925 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_maxiterations2_in_loop2931 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_CLOSE_LOOP_in_loop2936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_maxiterations2948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PARENTHESIS_in_bb2970 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_bb2972 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_COMMA_in_bb2974 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_INT_in_bb2976 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PARENTHESIS_in_bb2978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_MATRIX_in_pcm998 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_pcmrows_in_pcm1007 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_CLOSE_MATRIX_in_pcm1015 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_pcm1017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pcmrow_in_pcmrows1033 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_pcmrows_in_pcmrows1038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_MATRIX_ROW_in_pcmrow1051 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_pcmvalues_in_pcmrow1056 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CLOSE_MATRIX_ROW_in_pcmrow1061 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_pcmrow1063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_pcmvalues1076 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_pcmvalues_in_pcmvalues1081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_MATRIX_ID_in_pcmids1094 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_pcmidvalues_in_pcmids1099 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_MATRIX_ID_in_pcmids1104 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_NEWLINE_in_pcmids1106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_pcmidvalues1120 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_pcmidvalues_in_pcmidvalues1125 = new BitSet(new long[]{0x0000000000000002L});

}