/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculator {
	
	private final double DIVIDE_BY_ZERO = Double.longBitsToDouble(0x7ff0000000000000L);
	private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
	private double memoryStoreValue = 0.0;
	
	public String squared(String num) {
		if (isNumber(num)) {
			double parsedNum = Double.parseDouble(num);
			return Double.toString(parsedNum * parsedNum);
		} else {
			return "Invalid";
		}
	}
	
	public String sqrRoot(String num) {
		if (isNumber(num)) {
			double parsedNum = Double.parseDouble(num);
			return Double.toString(Math.sqrt(parsedNum));
		} else {
			return "Invalid";
		}
	}
	
	private Object evaluate(String equation) throws ScriptException {
		return scriptEngine.eval(equation);
	}
	
	public String eval(String display) throws DivideByZeroException {
		try {
			Double result = Double.parseDouble(evaluate(display).toString());	
			if (result == DIVIDE_BY_ZERO) {	
				throw new DivideByZeroException();															
			} else {
				return result.toString();	
			}
			
		} catch (ScriptException e) {
			return "Invalid";
		}
	}

	public boolean memoryStore(String num) {
		if (isNumber(num)) {
			double parsedNum = Double.parseDouble(num);
			memoryStoreValue = parsedNum;
			return true;
		}
		return false;
	}

	public String getMemoryValue() {
		return Double.toString(memoryStoreValue);
	}

	public void clearMemoryValue() {
		memoryStoreValue = 0.0;
	}

	public boolean plusMemoryValue(String num) {
		if (isNumber(num)) {
			double parsedNum = Double.parseDouble(num);
			memoryStoreValue += parsedNum;
			return true;
		} else {
			return false;
		}
	}

	public boolean minusMemoryValue(String num) {
		if (isNumber(num)) {
			double parsedNum = Double.parseDouble(num);
			memoryStoreValue -= parsedNum;
			return true;
		} else {
			return false; 
		}
	}
	
	private boolean isNumber(String num) {
		try {
			Double.parseDouble(num);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
