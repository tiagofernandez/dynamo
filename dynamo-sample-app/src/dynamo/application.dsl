Application SampleApp {

	Entity MyUser {
		[@Column(unique = true, nullable = false, length = 30)]
		String username
	
		[@Column(nullable = false, length = 100)]
		String password
	
		[@Column(nullable = false, length = 50)]
		String name
	
		[@Column(unique = true, nullable = false, length = 50) @Email]
		String email
	
		[@Basic(optional = false) @Enumerated(EnumType.STRING)]
		MyRole role
	
		[@ManyToMany(fetch = FetchType.LAZY)]
		Set<MyGroup> groups
	}
	
	Entity MyGroup {
		[@Column(unique = true, nullable = false, length = 30)]
		String name
		
		[@Column(length = 200)]
		String description
	
		[@ManyToMany(fetch = FetchType.LAZY)]
		Set<MyUser> users
	}
	
}