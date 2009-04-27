package dynamo.core.interceptor;

/**
 * MyService.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public interface MyService {

	void oneTwoThreeFour(MyFavoriteEntity myFavoriteEntity);
	
	void customValidation(MyHibernateEntity myHibernateEntity);

	void keepRunning(MyPreferredEntity myPreferredEntity);

	void keepWalking(MyOtherEntity myOtherEntity);
	
	void justDoIt(MyEntity myEntity);

	void justDoItAgain(MyEntity myEntity);
	
	void roundOneFight(MyRegularEntity myRegularEntity);
	
	void sitAndWait(MyFavoriteEntity myFavoriteEntity, MyPreferredEntity myPreferredEntity);
	
	void sitTight(MySpecialEntity mySpecialEntity);
	
	void yetAnotherService(MyDifferentEntity myDifferentEntity);
	
	void yetAnotherTest(MyExtendedEntity myExtendedEntity);
	
}