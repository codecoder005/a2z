package com.popcorn.a2z.docx;

import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.domain.response.EnrollmentResponse;
import com.popcorn.a2z.restapi.EnrollmentAPI;
import com.popcorn.a2z.search.SortingOrder;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EnrollmentAPIDocx extends EnrollmentAPI {
    @Operation(
            summary = "Enrollment related APIs",
            tags = {"EnrollmentAPI"},
            description = "This endpoint allows the creation of a new user. The request should include the necessary details for creating a user.",
            method = "POST",
            operationId = "8beeb49f-9beb-4862-b36a-b889b45ed908",
            security = {@SecurityRequirement(name = "basicAuth"), @SecurityRequirement(name = "bearerAuth")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload containing user details for creating a new user",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "EnrollmentRequest",
                                            summary = "Sample EnrollmentRequest",
                                            description = "Example request to enroll a new user with basic details",
                                            value = """
                                                    {
                                                      "isChallenged": false,
                                                      "isSalaried": true,
                                                      "age": 28,
                                                      "height": 175,
                                                      "weight": 72.350,
                                                      "salary": 55000.0,
                                                      "revenue": 2500000.00,
                                                      "email": "jane.doe@example.com",
                                                      "username": "jane_doe",
                                                      "password": "StrongP@ssw0rd!",
                                                      "firstName": "Jane",
                                                      "lastName": "Doe",
                                                      "gender": "FEMALE",
                                                      "dob": "1995-05-20",
                                                      "attendedOn": "2025-09-09",
                                                      "travellingOn": "2035-09-10",
                                                      "maturityDate": "2075-01-01",
                                                      "searchPattern": ".*",
                                                      "papers": [
                                                        "Learning Dynamics of LLM Fine-tuning — Yi Ren, Danica Sutherland",
                                                        "SAM 2: Segment Anything in Images and Videos — Nikhila Ravi",
                                                        "Faster Cascades via Speculative Decoding — Harikrishna Narasimhan",
                                                        "Transformers Learn Low Sensitivity Functions — Bhavya Vasudeva"
                                                      ],
                                                      "address": {
                                                        "addressLine1": "456 Elm Street",
                                                        "addressLine2": "Suite 12C",
                                                        "landmark": "Near City Mall",
                                                        "city": "Los Angeles",
                                                        "state": "CA",
                                                        "country": "USA",
                                                        "zipcode": "90001",
                                                        "addressType": "CURRENT"
                                                      }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            parameters = {
                    @Parameter(
                            name = "countryId", required = true, in = ParameterIn.PATH, example = "USA",
                            schema = @Schema(implementation = String.class, title = "Country Identifier"),
                            description =
                                    """
                                    A unique identifier representing the country where the user is being created.
                                    This is typically a country code (e.g., 'USA', 'IN').
                                    """
                    ),
                    @Parameter(
                            name = "sortBy", required = true, in = ParameterIn.QUERY, example = "John",
                            schema = @Schema(implementation = String.class, title = "sortBy"),
                            description = ""
                    ),
                    @Parameter(
                            name = "sortingOrder", required = false, in = ParameterIn.QUERY, example = "ASC",
                            schema = @Schema(implementation = SortingOrder.class, title = "sortingOrder"),
                            description = ""
                    ),
                    @Parameter(
                            name = "gender", required = true, in = ParameterIn.QUERY, example = "UNDISCLOSED",
                            schema = @Schema(implementation = EnrollmentRequest.Gender.class, title = "Gender"),
                            description = "Gender of the candidate"
                    ),
                    @Parameter(
                            name = "client-id", required = true, in = ParameterIn.HEADER, example = "A1234567",
                            schema = @Schema(implementation = String.class, title = "Client-Id"),
                            description = "A required header used to know who is calling this API."
                    ),
                    @Parameter(
                            name = "ip-address", required = false, in = ParameterIn.HEADER, example = "10.10.10.10",
                            schema = @Schema(implementation = String.class, title = "Channel-Type"),
                            description = "An optional header used to specify the type of channel."
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "success", content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EnrollmentResponse.class),
                            examples = {@ExampleObject(
                                    name = "EnrollmentResponse",
                                    summary = "Sample EnrollmentResponse",
                                    description = "Example response for enroll a new user with basic details",
                                    value = """
                                            {
                                              "isChallenged": false,
                                              "isSalaried": true,
                                              "age": 28,
                                              "height": 175,
                                              "weight": 72.350,
                                              "salary": 55000.0,
                                              "revenue": 2500000.00,
                                              "email": "jane.doe@example.com",
                                              "username": "jane_doe",
                                              "password": "StrongP@ssw0rd!",
                                              "firstName": "Jane",
                                              "lastName": "Doe",
                                              "gender": "FEMALE",
                                              "dob": "1995-05-20",
                                              "attendedOn": "2025-09-09",
                                              "travellingOn": "2035-09-10",
                                              "maturityDate": "2075-01-01",
                                              "searchPattern": ".*",
                                              "papers": [
                                                "Learning Dynamics of LLM Fine-tuning — Yi Ren, Danica Sutherland",
                                                "SAM 2: Segment Anything in Images and Videos — Nikhila Ravi",
                                                "Faster Cascades via Speculative Decoding — Harikrishna Narasimhan",
                                                "Transformers Learn Low Sensitivity Functions — Bhavya Vasudeva"
                                              ],
                                              "address": {
                                                "addressLine1": "456 Elm Street",
                                                "addressLine2": "Suite 12C",
                                                "landmark": "Near City Mall",
                                                "city": "Los Angeles",
                                                "state": "CA",
                                                "country": "USA",
                                                "zipcode": "90001",
                                                "addressType": "CURRENT"
                                              }
                                            }
                                            """
                            )}
                    )}),
                    @ApiResponse(responseCode = "400", description = "bad request", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))}),
                    @ApiResponse(responseCode = "401", description = "unauthorized", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))}),
                    @ApiResponse(responseCode = "500", description = "internal server error", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))}),
                    @ApiResponse(
                            responseCode = "503",
                            description = "service unavailable",
                            links = {
                                    @Link(
                                            name = "retryAfter",
                                            operationId = "retryServiceOperation", // Operation that handles retry
                                            description = "Link to retry the service after a certain period",
                                            parameters = {@LinkParameter(name = "retry-time")}
                                    ),
                                    @Link(
                                            name = "serviceStatus",
                                            operationRef = "/service-status",  // A reference to an existing operation in the OpenAPI definition
                                            description = "Link to check the service status",
                                            parameters = {@LinkParameter(name = "userId")}
                                    )},
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Map.class),
                                    examples = {@ExampleObject(
                                            name = "exception detail response",
                                            value = """
                                                    {
                                                      "status": 503,
                                                      "message": "Internal server error",
                                                      "timestamp": "2024-11-15T19:33:52.955Z"
                                                    }
                                                    """,
                                            description = "Example of an error response when the service is unavailable. The 'status' field indicates the HTTP status code, 'message' provides a description of the error, and 'timestamp' records when the error occurred."
                                    )}
                            )})
            },
            externalDocs = @ExternalDocumentation(
                    description = "Find more details on the API usage",
                    url = "https://example.com/api-docs/user-management",
                    extensions = {
                            @Extension(name = "x-api-version", properties = {@ExtensionProperty(name = "version", value = "1.0", parseValue = true)}),
                            @Extension(name = "x-support", properties = {
                                    @ExtensionProperty(name = "contact", value = "support@example.com", parseValue = false),
                                    @ExtensionProperty(name = "phone", value = "+1-800-123-4567", parseValue = false)}
                            )
                    }
            )
    )
    ResponseEntity<EnrollmentResponse> enroll(EnrollmentRequest enrollmentRequest);
}
