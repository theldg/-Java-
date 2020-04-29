package 栈;

import java.util.HashMap;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/
 * 
 * @author Administrator
 *
 */
public class _20_有效的括号 {
	// 利用栈的后进先出
	public boolean isValid(String s) {

		char[] strings = s.toCharArray();
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < strings.length; i++) {
			char left = strings[i];
			if ((left == '(') | (left == '[') | (left == '{')) {
				// stack.add(strings[i]);
				stack.push(strings[i]);

			} else {
				if (stack.isEmpty()) {
					return false;
				} else {
					left = stack.pop();
					if (left == '(' && strings[i] != ')')
						return false;
					if (left == '[' && strings[i] != ']')
						return false;
					if (left == '{' && strings[i] != '}')
						return false;

				}
			}
		}
		return stack.isEmpty();

	}

	// 很多人可能会误解replace()是替换单个，而replaceAll是替换全部，
	// 其实这是错的（我以前也是这么想的- -）。
	// replace()只是没有用到正则表达式，但会替换所有匹配的字符串.
	// replaceAll()、replaceFirst()会用到正则表达式。
	public boolean isValid2(String s) {

		while (s.contains("()") || s.contains("[]") || s.contains("{}")) {
			s = s.replace("()", "");
			s = s.replace("[]", "");
			s = s.replace("{}", "");
		}
		return s.isEmpty();
	}

	// 利用栈的后进先出
	// 利用hashmap的key value对应
	public boolean isValid3(String s) {
		HashMap<Character, Character> a = new HashMap<Character, Character>();
		a.put('(', ')');
		a.put('[', ']');
		a.put('{', '}');
		char[] strings = s.toCharArray();
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < strings.length; i++) {
			char left = strings[i];
			if (a.containsKey(left)) {
				// stack.add(strings[i]);
				stack.push(left);

			} else {
				if (stack.isEmpty()) {
					return false;
				} else {
					left = stack.pop();
					if (a.get(left) != strings[i])
						return false;

				}
			}
		}
		return stack.isEmpty();

	}
}
