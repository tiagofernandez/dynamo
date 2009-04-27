grammar Dynamo;

@lexer::header {
package dynamo.plugins.maven.parser;

import dynamo.plugins.maven.model.*;
}

@parser::header {
package dynamo.plugins.maven.parser;

import dynamo.plugins.maven.model.*;
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

aplication returns [Application app]
	: 'Application' appName=STRING '{' { $app = new Application($appName.text); }
	( e=entity { $app.addEntity(e); } )+
	/*
	( en=enumeration { $app.addEnumeration(en); } )*
	( s=service { $app.addService(s); } )+
	*/
	'}' EOF ;

entity returns [Entity e]
	: 'Entity' eName=STRING '{' { $e = new Entity($eName.text); }
		( ( ANNOTATION ) attributeType attrName=STRING 
			{ $e.addAttributeAnnotation($attrName.text, $ANNOTATION.text); }
			{ $e.addAttributeType($attrName.text, $attributeType.text); } 
		)+
	'}' ;

attributeType 
	: STRING | 'List<' STRING '>' | 'Set<' STRING '>' ;

/*
enumeration returns [Enumeration e]
	: 'Enumeration' eName=STRING '{' { $e = new Enumeration($eName.text); }
		( value=STRING { $e.addValue($value.text); } )+
	'}' ;

service	returns [Service s]
	: 'Service' sName=STRING '{' { $s = new Service($sName.text); }
		( eName=STRING '(' 
			operType=operationType { $s.addOperation($eName.text, $operType.text); } 
			( ',' operType=operationType { $s.addOperation($eName.text, $operType.text); } )* 
		')' )+ 
	'}' ;

operationType
	: 'delete' | 'find' | 'list' | 'save' ;
*/


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

ANNOTATION	: '[' ( options { greedy=false; } : . )* ']' ;

STRING		: ('a'..'z' | 'A'..'Z' | '0'..'9' )+ ;

WS		: (' ' |'\t' | '\r' | '\n')+ { skip(); } ;
