package com.pugwoo.test;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class GetStarted {

	public static void main(String[] args) throws MalformedPatternException {
	    Pattern pattern = new Perl5Compiler().compile("\n\\[.*@.*\\][#,$]$",Perl5Compiler.DEFAULT_MASK|Perl5Compiler.SINGLELINE_MASK);  
	    Perl5Matcher matcher = new Perl5Matcher();  
	    PatternMatcherInput matcherInput = new PatternMatcherInput(" echo hello world\r\nhello world\r\n[root@localhost ~]#");  
	    while (matcher.contains(matcherInput, pattern)) {  
	        MatchResult result = matcher.getMatch();  
	        System.out.println(result.toString());  
	    }  
	}
}
