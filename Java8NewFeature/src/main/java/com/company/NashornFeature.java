package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Nashorn JavaScript引擎
 */
public class NashornFeature {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        System.out.println(scriptEngine.getClass().getName());
        System.out.println(scriptEngine.eval("function f() {return 1;} f() + 1;"));
    }
}
