package com.my.servlet;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;


public class Calc extends HttpServlet {

    private class OpParams{
        private HttpServletRequest request;
        private HttpServletResponse response;

        OpParams(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
            firstValueString = request.getParameter("value").trim();
            secondValueString = request.getParameter("secvalue").trim();
            resultString = request.getParameter("result");
            operations =request.getParameter("operations");

            if (!resultString.isEmpty()){
                result = Double.parseDouble(resultString);
            }

            firstOperand = Double.parseDouble(firstValueString);
            secondOperand = Double.parseDouble(secondValueString);
        }

        public String firstValueString;
        public String secondValueString;
        public String operations;
        public String resultString;
        public Double result;
        public double firstOperand;
        public double secondOperand;

        public boolean validate() throws ServletException, IOException {
            if (secondValueString==null || secondValueString.length()==0 || !(secondValueString.matches ("(\\d+)|(\\d+\\.\\d+)"))){
                printError(request, response, "Second operand is invalid ");
                return false;
            }
            if (firstValueString==null || firstValueString.length()==0 || !(firstValueString.matches ("(\\d+)|(\\d+\\.\\d+)"))){
                printError(request, response, "First operand is invalid ");
                return false;
            }
            return true;
        }
    }

    private static final long serialVersinUID = 1L;
    public static final String PLUS_OPERATION = "plus";
    public static final String MINUS_OPERATION = "minus";
    public static final String DIVIDE_OPERATION = "divide";
    public static final String MULTIPLY_OPERATION = "multiply";
    public static final String SQUARE_OPERATION = "square";
    public static final String ROOT_OPERATION = "root";
    private static Logger logger = Logger.getLogger(Calc.class);

    public Calc() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("POST request from " + request.getRemoteHost());
        logger.debug("Query string is " + request.getQueryString());

        OpParams params = new OpParams(request, response);
        if (!params.validate()) return;

        try {
            Map parameters = request.getParameterMap();
            if (parameters.containsKey("enter")) {
                params.result = performOperation(params.operations, params.firstOperand, params.secondOperand);
            }
            if (params.result % 1 == 0.0) {
                request.setAttribute("result", params.result.intValue());
            } else
                request.setAttribute("result", params.result);
            request.getRequestDispatcher("Calc.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            logger.error("", e);
            printError(request, response, "Please fill all blocks");
        }
    }

    private double performOperation(String operationName, double firstOperand, double secondOperand) {

        logger.debug("performing operation "+operationName+" with operands ");
        if (operationName.equals(PLUS_OPERATION)) {
            double result = firstOperand + secondOperand;
            return (double)Math.round(result * Math.pow(10, 14))/Math.pow(10, 14);
        } else if (operationName.equals(MINUS_OPERATION)) {
            double result = firstOperand - secondOperand;
            return (double)Math.round(result * Math.pow(10, 14))/Math.pow(10, 14);
        } else if (operationName.equals(DIVIDE_OPERATION)) {
            return firstOperand/secondOperand;
        } else if (operationName.equals(MULTIPLY_OPERATION)) {
            return firstOperand*secondOperand;
        } else if (operationName.equals(SQUARE_OPERATION)) {
            return Math.pow(firstOperand, secondOperand);
        } else if (operationName.equals(ROOT_OPERATION)) {

            return Math.pow(firstOperand, 1 / secondOperand);
        }
        return 0;
    }

    private void printError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);

        logger.error(message);
        RequestDispatcher view = request.getRequestDispatcher("Calc.jsp");
        view .forward(request, response);
    }

protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
