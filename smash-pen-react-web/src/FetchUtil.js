const fetchGetInit = (body) => {
	return fetchInit("GET", body);
};

const fetchPostInit = (body) => {
    return fetchInit("POST", body)
};

const fetchPutInit = (body) => {
    return fetchInit("PUT", body)
};

const fetchInit = (method, body) => {
    return {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        },
        body: JSON.stringify(body)
    };
};

export { fetchGetInit, fetchPostInit, fetchPutInit };