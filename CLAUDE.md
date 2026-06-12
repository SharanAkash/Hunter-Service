# Hunter Service - AI Development Guide

## Project Overview
Hunter Service is a Java-based API test automation framework using Playwright for API testing. It tests the GoREST API (https://gorest.co.in) with comprehensive validation, reporting, and BDD support.

## Tech Stack
- **Language**: Java 11
- **Build Tool**: Maven
- **Testing Framework**: TestNG, Cucumber (BDD)
- **API Client**: Microsoft Playwright
- **JSON Processing**: Jackson (Fasterxml)
- **Reporting**: Allure Reports
- **Validation**: TestNG Assertions

## Project Structure
```
Hunter-service/
├── src/
│   ├── main/java/
│   │   ├── BuildingRequest/     # Request payload builders
│   │   ├── constants/            # BaseUrl, EndPoint, UserConstants
│   │   ├── container/            # Variable containers for data sharing
│   │   ├── Helaper/              # API helper classes (note: typo is intentional)
│   │   ├── pojo/                 # Plain Old Java Objects for data models
│   │   └── com/hunter/utils/     # Utilities (CSV generation, etc.)
│   └── test/java/
│       ├── com/hunter/feature/   # Test classes and step definitions
│       └── Validation/           # Response validation classes
├── data/                         # CSV files for test data
├── allure-results/              # Allure test results
└── pom.xml                      # Maven dependencies
```

## Coding Standards

### 1. API Helper Pattern
- All API calls must go through Helper classes in `Helaper/` package
- Helper methods should:
  - Build complete URLs from BaseUrl + EndPoint constants
  - Pass RequestOptions to Playwright's get/post methods (NOT concatenate to URL)
  - Print URL and curl command for debugging
  - Include Allure reporting steps
  - Return APIResponse objects

**Example:**
```java
public APIResponse getUserApiHelper() throws Exception {
    String url = BaseUrl.BASE_URL + EndPoint.GET_USER;
    APIResponse response = apiRequestContext.get(url);
    // Add logging and Allure reporting
    return response;
}
```

### 2. RequestOptions Usage
**IMPORTANT**: Never concatenate RequestOptions objects to URL strings.
```java
// ❌ WRONG
String url = baseUrl + "?" + requestOptions;

// ✅ CORRECT
RequestOptions options = RequestOptions.create()
    .setQueryParam("id", id);
apiRequestContext.get(url, options);
```

### 3. Validation Pattern
- All validations go in `Validation/` package
- Each validation method should:
  - Use ObjectMapper to parse JSON responses
  - Assert response status (200, 201, etc.)
  - Validate response structure (isArray, isObject, etc.)
  - Compare fields using TestNG assertions
  - Provide clear assertion messages with context
  - Log validation progress

### 4. Naming Conventions
- **Classes**: PascalCase (e.g., `GetUserApiHelper`)
- **Methods**: camelCase, descriptive (e.g., `getUserApiQueryParameterHelper`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `BASE_URL`)
- **Variables**: camelCase
- **Packages**: lowercase (note: `Helaper` and `Validation` use capital first letter - maintain consistency)

### 5. Error Handling
- All API methods should throw `Exception`
- Use try-catch only when recovery is possible
- Provide meaningful assertion messages

### 6. JSON Processing
- Always use Jackson ObjectMapper for JSON parsing
- Use JsonNode for flexible JSON traversal
- Parse arrays with `jsonNode.isArray()` and iterate with `jsonNode.get(index)`

### 7. Test Data Management
- Store test data in CSV files under `data/`
- Use `CsvFileGeneration` utility for CSV operations
- Validate API responses against CSV data when applicable

### 8. Logging and Reporting
- Use `System.out.println()` for console logs
- Include Allure steps with `Allure.step()`
- Attach relevant data with `Allure.addAttachment()`
- Log: Request URL, Curl command, Response status, Response body

## Development Guidelines

### When Adding New API Tests:
1. Create endpoint constant in `constants/EndPoint.java`
2. Create helper method in appropriate Helper class
3. Create POJO if request/response needs object mapping
4. Create validation method in `Validation/` package
5. Create test method in `com/hunter/feature/`
6. Update CSV test data if needed

### When Fixing Bugs:
1. Read the entire affected file first
2. Understand the Playwright API - never concatenate objects to strings
3. Test locally before committing
4. Ensure Allure reports are generated correctly

### When Validating Responses:
1. Parse JSON with ObjectMapper
2. Find matching records by ID or unique field
3. Validate ALL fields (id, name, email, gender, status, etc.)
4. Provide context in assertion messages
5. Log successful validations

## Common Patterns

### API Request with Query Parameters:
```java
RequestOptions options = RequestOptions.create()
    .setQueryParam("id", id)
    .setQueryParam("status", status);
String url = BaseUrl.BASE_URL + EndPoint.GET_USER;
APIResponse response = apiRequestContext.get(url, options);
```

### JSON Array Validation:
```java
ObjectMapper mapper = new ObjectMapper();
JsonNode jsonResponse = mapper.readTree(response.body());
Assert.assertTrue(jsonResponse.isArray(), "Response should be an array");

for (int i = 0; i < jsonResponse.size(); i++) {
    JsonNode user = jsonResponse.get(i);
    Assert.assertEquals(user.get("id").asInt(), expectedId);
}
```

### Cross-Response Validation:
When validating filtered response against full response by ID:
```java
for (JsonNode filteredUser : filteredResponse) {
    int id = filteredUser.get("id").asInt();
    // Find matching user in full response
    JsonNode matchingUser = findById(fullResponse, id);
    // Validate all fields match
    Assert.assertEquals(filteredUser.get("name").asText(), 
                       matchingUser.get("name").asText());
}
```

## Testing
- Run tests: `mvn test`
- Generate Allure report: `mvn allure:serve`
- Run specific test: `mvn -Dtest=GetUserApiTest test`

## Important Notes
1. **Never modify URLs directly** - use BaseUrl and EndPoint constants
2. **RequestOptions are Playwright objects** - pass them to API methods, don't concatenate
3. **Maintain existing package structure** - including naming quirks (Helaper, Validation)
4. **All API responses must include Allure reporting**
5. **CSV validation is critical** - maintain data integrity
6. **Test against GoREST API** - respect rate limits

## AI-Specific Instructions
- Before making changes, read the complete file to understand context
- Follow existing patterns and naming conventions exactly
- Never assume Playwright API behavior - it has specific parameter patterns
- When validating, compare ALL fields, not just ID
- Include proper error messages in assertions
- Maintain backward compatibility with existing tests
- Ask clarifying questions if requirements are ambiguous
