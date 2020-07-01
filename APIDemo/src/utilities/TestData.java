package utilities;

public class TestData {

	    private String baseURI;
	    public String getBaseURI() {
			return baseURI;
		}

		public void setBaseURI(String baseURI) {
			this.baseURI = baseURI;
		}

		public String getResource() {
			return resource;
		}

		public void setResource(String resource) {
			this.resource = resource;
		}

		public String getRequestMethod() {
			return requestMethod;
		}

		public void setRequestMethod(String requestMethod) {
			this.requestMethod = requestMethod;
		}

		private String resource;;
	    private String requestMethod;
	    
	 
	    public TestData(){}
	 
	    public TestData(String baseURI, String resource, String requestMethod) {
	        this.baseURI = baseURI;
	        this.resource = resource;
	        this.requestMethod = requestMethod;
	    }
	 
	   //getters and setter..
	 
	    @Override
	    public String toString() {
	        return " base URI "+baseURI+ ": resource "+resource+ " requestMethod "+requestMethod;
	    }
    
}
