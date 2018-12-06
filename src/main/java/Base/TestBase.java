package Base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;

public class TestBase {
	/**
	 * Description: Data-provider to get data for  testNG.xml file and return through HashMap
	 * @param m :- reflection class Method object, which refers to the @Test method which is calling this data-provider
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	@DataProvider()
    public Object[][] getParameters(Method m, ITestContext ctx) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		
        for (XmlClass thisClass : ctx.getCurrentXmlTest().getXmlClasses()) {
        	
        	if (thisClass.getName().equals(this.getClass().getName())){
        		Map<String, String> classParams = new HashMap<String, String>();
        		classParams = thisClass.getAllParameters();
        		
        		if (classParams.size() > 0){
        			params.putAll(classParams);
        		}
        	
        		for (XmlInclude method : thisClass.getIncludedMethods()) {
                    if (method.getName().equals(m.getName())) {
                    	Map<String, String> methodParams = new HashMap<String, String>();
                    	
                    	methodParams = method.getAllParameters();
                    	
                    	if (methodParams.size() > 0){
                    		params.putAll(methodParams);
                    		break;
                    	}
                    	
                    }
                }
        	
        	}
            
        }
        return new Object[][] { { params } };
    }
}
