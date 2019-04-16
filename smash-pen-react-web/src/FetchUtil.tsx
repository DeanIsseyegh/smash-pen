const fetchGetInit = (body?: any): RequestInit => {
	return fetchInit("GET", body);
};

const fetchPostInit = (body: any): RequestInit => {
    return fetchInit("POST", body)
};

const fetchPutInit = (body: any): RequestInit => {
    return fetchInit("PUT", body)
};

const fetchInit = (method: string, body: any): RequestInit => {
    const headers = new Headers();
    const token = localStorage.getItem('token');
    headers.append('Authorization', token ? token : '');
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');
    return {
        method: method,
        headers: headers,
        body: JSON.stringify(body)
    };
};

export { fetchGetInit, fetchPostInit, fetchPutInit };
