package eu.exadelpractice.registry.common.client;

import java.util.Arrays;
import java.util.List;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public abstract class GenericClientImpl<T, E extends ObjectNotFoundException> {
	@Autowired
	private ApiProperties apiProperties;
	@Autowired
	private  RestTemplate restTemplate;
	private final Class<T> type;
	//private final Class<E> exceptionType;
	private final String uri;
	
	protected GenericClientImpl(Class<T> type, String uri) {
		this.type =  type;
		this.uri = uri;
	}

	public T getObjectById(String id) throws E, InternalServerErrorException, BadRequestException {
		// restTemplate = new RestTemplate();
		HttpHeaders headers = getRequestHeaders();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		String url = apiProperties.getApiHost() + uri + id;
		try {
			ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, request, type);
			T object = response.getBody();
			return object;
		} catch (HttpClientErrorException ex) {
			handleHttpClientErrorException(url, ex);
		} catch (Exception ex) {
			log.error("Error happened during rest call url: {} with id: {}", url, id, ex);
			throw new InternalServerErrorException(ex.getMessage());
		}
		return null;
	}

	public List<T> getAllObjects(Class<T[]> arrayType) throws E, InternalServerErrorException, BadRequestException {
		HttpHeaders headers = getRequestHeaders();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		String url = apiProperties.getApiHost() + uri;
		try {
			//List<T> arr1 = new ArrayList<T>();
			//arr1.toArray(a);
			ResponseEntity<T[]> response = restTemplate.exchange(url, HttpMethod.GET, request, arrayType);
			T[] ps = response.getBody();
			//@SuppressWarnings("unchecked")
			List<T> objects = (List<T>) Arrays.asList(ps);
			return objects;
		} catch (HttpClientErrorException ex) {
			handleHttpClientErrorException(url, ex);
		} catch (Exception ex) {
			log.error("Error happened during rest call url: {}", url, ex);
			throw new InternalServerErrorException(ex.getMessage());
		}
		return null;
	}

	public String createObject(T object) throws InternalServerErrorException, E, BadRequestException {
		HttpHeaders headers = getRequestHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<T> request = new HttpEntity<T>(object, headers);
		String url = apiProperties.getApiHost() + uri;
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			String id = response.getBody();
			return id;
		} catch (HttpClientErrorException ex) {
			handleHttpClientErrorException(url, ex);
		} catch (Exception ex) {
			log.error("Error happened during rest call url: {}", url, ex);
			throw new InternalServerErrorException(ex.getMessage());
		}
		return null;
	}
	public void updateObject(T object) throws E, BadRequestException, InternalServerErrorException {
		HttpHeaders headers = getRequestHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<T> request = new HttpEntity<T>(object, headers);
		String url = apiProperties.getApiHost() + uri;
		try {
			restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
		} catch (HttpClientErrorException ex) {
			handleHttpClientErrorException(url, ex);
		} catch (Exception ex) {
			log.error("Error happened during rest call url: {}", url, ex);
			throw new InternalServerErrorException(ex.getMessage());
		}
	}
	public void deleteObjectById(String id) throws E, InternalServerErrorException, BadRequestException {
		HttpHeaders headers = getRequestHeaders();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		String url = apiProperties.getApiHost() + uri + id;

		try {
			restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
		} catch (HttpClientErrorException ex) {
			handleHttpClientErrorException(url, ex);
		} catch (Exception ex) {
			log.error("Error happened during rest call url: {}", url, ex);
			throw new InternalServerErrorException(ex.getMessage());
		}
	}

	public ResponseEntity<String> findOrCreateObject(T object) throws E, BadRequestException, InternalServerErrorException {
		HttpHeaders headers = getRequestHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<T> request = new HttpEntity<T>(object, headers);
		
		String url = apiProperties.getApiHost() + uri + "find/create/";
		
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			return response;
		} catch (HttpClientErrorException ex) {
			handleHttpClientErrorException(url, ex);
		} catch (Exception ex) {
			log.error("Error happened during rest call url: {}", url, ex);
			throw new InternalServerErrorException(ex.getMessage());
		}
		return null;
	}

	private T handleHttpClientErrorException(String url, HttpClientErrorException ex) throws E, InternalServerErrorException, BadRequestException {
		log.error("HTTPClientError happened during rest call url: {}", url, ex);
		if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw createDomainNotFoundException(ex);
		} else {
			throw new BadRequestException(ex.getMessage());
		}
	}

	protected abstract E createDomainNotFoundException(HttpClientErrorException ex);

	public HttpHeaders getRequestHeaders() {
		String plainCreds = apiProperties.getApiUserName() + ":" + apiProperties.getApiPassword();
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		return headers;
	}
}
