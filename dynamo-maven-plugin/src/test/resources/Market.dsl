Application Market {

	Entity Buyer {
		[@Column(nullable = false, unique = true, length = 50)]
		String name
		
		[@ManyToMany(fetch = FetchType.LAZY)]
		List<Stock> stocks
	}
	
	Entity Seller {
		[@Column(nullable = false, unique = true, length = 50)]
		String name
		
		[@ManyToMany(fetch = FetchType.LAZY)]
		List<Stock> stocks
	}
	
	Entity Stock {
		[@Column(nullable = false, unique = true, length = 5)]
		String code
		
		[@Basic]
		Double marketValue
		
		[@Transient]
		Date lastUpdate
	}
	
}