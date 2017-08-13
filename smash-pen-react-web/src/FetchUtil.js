const fetchGetInit = () => {
	return {
		method: "GET",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
			'Authorization': localStorage.getItem('token')
		}
	};
};

const fetchPostInit = (body) => {
	return {
		method: "POST",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
			'Authorization': localStorage.getItem('token')
		},
		body: JSON.stringify(body)
	};
};

export { fetchGetInit, fetchPostInit };