import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

class Interpreter {

    public static void main(String args[]) {

        // System.out.print("\n\n Hello world! \n\n");

    }


    // List<? Extends Object> args = ArrayList<? Extends Object>;

    /**
     * 
     * dispatcher( )
     * 
     * @throws Exception
     * 
     */
    public void dispatcher(String str) throws Exception {

        if (str.charAt(0) == '$') {

            this.assignationHandler(str);

        } else if (str.substring(0, 1) == "IF") {

            this.ifHandler(str);
            
        } else if (str.substring(0, 4) == "WHILE") {

            this.whileHandler(str);
            
        } else if (str.substring(0, 4) == "PRINT") {

            System.out.println( str.split("\"")[1] );
        }
    }

    /**
     *
     * whileHandler( )
     * 
     * @throws Exception
     * 
     */
    private String whileHandler(String str) throws Exception {

        String[] paramCondition = str.substring(0, str.indexOf("DO")).split(" ");
        String istructions = str.substring((str.indexOf("DO")+2)).trim();

        switch (paramCondition[2]) {

            case ">": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == 1), istructions);
            }
            case ">=": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == 1), istructions);
            }
            case "==": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == 0), istructions);
            }
            case "<": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == -1), istructions);
            }
            case "<=": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == -1), istructions);
            }
        }

        return "";
    }

    /**
     *
     * ifHandler( )
     * 
     * @throws Exception
     * 
     */
    private String ifHandler(String str) throws Exception {

        // get condition

        String[] paramCondition = str.substring(0, str.indexOf("THEN")).split(" ");
        String strThen = str.substring((str.indexOf("THEN")+5), str.indexOf("ELSE")).trim();
        String strElse = str.substring((str.indexOf("ELSE")+4), str.length()).trim();

        switch (paramCondition[2]) {

            case ">": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == 1), strThen, strElse);
            }
            case ">=": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == 1), strThen, strElse);
            }
            case "==": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == 0), strThen, strElse);
            }
            case "<": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == -1), strThen, strElse);
            }
            case "<=": {
                this.handleCondition((paramCondition[1].compareTo(paramCondition[3]) == -1), strThen, strElse);
            }

            // case ">": {
            //     if (paramCondition[1].compareTo(paramCondition[3]) == 1) { this.exec(strThen); } else { this.exec(strElse); }
            // }
            // case ">=": {
            //     if (paramCondition[1].compareTo(paramCondition[3]) == 1) { this.exec(strThen); } else { this.exec(strElse); }
            // }
            // case "==": {
            //     if (paramCondition[1].compareTo(paramCondition[3]) == 0) { this.exec(strThen); } else { this.exec(strElse); }
            // }
            // case "<": {
            //     if (paramCondition[1].compareTo(paramCondition[3]) == -1) { this.exec(strThen); } else { this.exec(strElse); }
            // }
            // case "<=": {
            //     if (paramCondition[1].compareTo(paramCondition[3]) == -1) { this.exec(strThen); } else { this.exec(strElse); }
            // }
        }
        
        return "";
    }

    /**
     *
     * handleCondition()
     * 
     * @throws Exception
     * 
     */
    private void handleCondition(boolean condition, String... cmd) throws Exception {

        if (condition)
            this.exec(cmd[0]);
        else if (cmd.length > 1)
            this.exec(cmd[1]);

    }

    /**
     *
     * exec()
     * 
     * @throws Exception
     * 
     */
    private void exec(String str) throws Exception {

        for (String cmd : str.split(":")) {

            this.dispatcher(cmd);
        }
    }

    /**
     *
     * assignationHandler( )
     * 
     * @throws Exception
     * 
     */
    private String assignationHandler(String str) throws Exception {
        
        String paramsExpression = str.substring(4, str.length());

        if (paramsExpression.contains("+")) {

            /**
             *     Sum or Concatenation
             */

            if (paramsExpression.contains("\"")) {
                /**
                 *     Concatenation
                 */

                // check if are all string
                if (
                    paramsExpression.charAt(0) == '\"' && 
                    paramsExpression.charAt(paramsExpression.length()-1) == '\"' && 
                    paramsExpression.substring(1, paramsExpression.length()-2).split("\"+\"").length > 0
                )
                    return String.join("\"+\"", paramsExpression.substring(1, paramsExpression.length()-1));

                throw new Exception("Formato non corretto!");
                
            } else {
                /**
                 *     Sum
                 */
                
                List<String> paramsArray = Arrays.asList(paramsExpression.split("+"));

                int res = paramsArray
                            .stream()
                            .map(s -> Integer.parseInt(s))
                            .reduce(0, (total, accumulator) -> total + accumulator)
                ;

                return String.valueOf(res);
            }        
        } else {

            /**
             *  TODO
             *  OTHER OPERATION 
             */
        }        

        /**
         *     Simple Assignation 
         */
        
        if (paramsExpression.contains("\"")) {
            /**
             *     String Assignation
             */

            return paramsExpression.substring(1, paramsExpression.length()-1);

        } 
        
        // else if( paramsExpression.contains("true") || paramsExpression.contains("false") ) {
            /**
             *     Boolean Assignation 
             */
            
            // this.booleanAssignation

        // } else {
            /**
             *     Number Assignation 
             */

                // this.numberAssignation
        // }

        return paramsExpression.trim();
    }


}