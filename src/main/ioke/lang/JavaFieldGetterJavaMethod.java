/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package ioke.lang;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ioke.lang.exceptions.ControlFlow;

/**
 *
 * @author <a href="mailto:ola.bini@gmail.com">Ola Bini</a>
 */
public class JavaFieldGetterJavaMethod extends Method implements JavaImplementedMethod {
    private Class declaringClass;
    private Field field;

    public JavaFieldGetterJavaMethod(Field field) {
        super(field.getName());
        this.field = field;
        this.declaringClass = field.getDeclaringClass();
    }

    public String getArgumentsCode() {
        return "...";
    }

    @Override
    public Object activate(IokeObject self, IokeObject context, IokeObject message, Object on) throws ControlFlow {
        try {
            if((on instanceof IokeObject) && (IokeObject.data(on) instanceof JavaWrapper)) {
                Object obj = ((JavaWrapper)IokeObject.data(on)).getObject();
                if(!(declaringClass.isInstance(obj))) {
                    obj = obj.getClass();
                }

                Object result = field.get(obj);
                if(result == null) {
                    return context.runtime.nil;
                } else if(result instanceof Boolean) {
                    return ((Boolean)result).booleanValue() ? context.runtime._true : context.runtime._false;
                }
                return result;
            } else {
                Object obj = on;
                if(!(declaringClass.isInstance(obj))) {
                    obj = obj.getClass();
                }

                Object result = field.get(obj);
                if(result == null) {
                    return context.runtime.nil;
                } else if(result instanceof Boolean) {
                    return ((Boolean)result).booleanValue() ? context.runtime._true : context.runtime._false;
                }
                return result;
            }
        } catch(Exception e) {
            context.runtime.reportJavaException(e, message, context);
            return context.runtime.nil;
        }
    }
    
    @Override
    public String inspect(Object self) {
        return "method(" + declaringClass.getName() + "_" + field.getName() + ")";
    }
}// JavaFieldGetterJavaMethod
