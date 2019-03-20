package org.beetl.core.om.asm;


import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.beetl.core.BasicTestCase;
import org.objectweb.asm.tree.FieldNode;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;


public class ASMBeanFactoryTest extends BasicTestCase {

	private static final String CLASS_NAME = User.class.getName();

	@Test
	public void testGetter() throws Exception {

		User user = new User();
		user.setName("shaozuo");
		user.setAddress("北京");
		user.setNumbers(15);
		user.setBirthDate(new Date());
		user.setAge((short) 12);
		user.setDistance(44L);
		user.setFlag((byte) 1);
		user.setIsManager(false);
		user.setHeight(1.73F);
		user.setGender('M');
		user.setAa(12);
		user.setBB(13);

		ClassDescription classDescription = BeanEnhanceUtils.getClassDescription(CLASS_NAME);
		for (List<FieldNode> nodes : classDescription.fieldMap.values()) {
			for (FieldNode node : nodes) {
				System.out.println(node.name + ":" + ASMBeanFactory.value(user, node.name));
				AssertJUnit.assertEquals(getValue(user, node.name), ASMBeanFactory.value(user, node.name));
			}
		}
		AssertJUnit.assertEquals("哈哈是", ASMBeanFactory.value(user, "填写"));
		AssertJUnit.assertEquals("哈哈是", ASMBeanFactory.value(user, "写"));
		AssertJUnit.assertEquals("哈哈是", ASMBeanFactory.value(user, "填"));
	}


	private static Object getValue(User user, String attrName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = user.getClass().getDeclaredField(attrName);
		field.setAccessible(true);
		return field.get(user);
	}

}