let urlPrefix = '';

export function AjaxError(...args) {
    const [response, options, responseBody] = args;

    this.ajax = true;
    this.options = options;
    this.status = responseBody.httpStatus || response.status;
    this.statusText = responseBody.message || response.statusText;
    this.responseBody = responseBody;

    const err = Error.call(this, this.status + ' ' + this.statusText);
    err.name = this.name = 'AjaxError';
    this.message = err.message;
}

function setUrlPrefix(prefix) {
    urlPrefix = prefix;

    if (urlPrefix.charAt(urlPrefix.length - 1) !== '/') {
        urlPrefix += '/';
    }
}

function checkStatus(options, response) {
    if (response.status < 200 || response.status >= 300) {
        return response.text().then((responseBody) => {
            const contentType = response.headers.get('content-type');

            if (contentType && contentType.indexOf('application/json') !== -1) {
                try {
                    responseBody = JSON.parse(responseBody);
                } catch (e) {
                    console.warn('Could not jsonparse response body', responseBody);
                }
            }

            throw new AjaxError(response, options, responseBody);
        });
    }

    return response;
}

function parse(options, response) {
    const contentType = response.headers.get('content-type');

    const parsedPromise = contentType && contentType.indexOf('application/json') !== -1 ?
        response.json() : response.text();

    return parsedPromise.then(responseBody => {
        if (responseBody.httpStatus < 200 || responseBody.httpStatus >= 300) {
            throw new AjaxError(response, options, responseBody);
        }

        return responseBody;
    });
}

function getUrl(url) {
    return (urlPrefix || '') + url;
}

function get(url, config = {}) {
    const options = {
        ...config,
        url,
        headers: config.headers,
        credentials: 'same-origin'
    };

    return fetch(getUrl(url), options)
        .then(response => checkStatus(options, response))
        .then(response => parse(options, response));
}

function post(url, data = {}, config = {}) {
    const options = {
        url,
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            ...config.headers
        },
        credentials: 'same-origin'
    };

    return fetch(getUrl(url), options)
        .then(response => checkStatus(options, response))
        .then(response => parse(options, response));
}

function put(url, data = {}, config = {}) {
    const options = {
        url,
        method: 'PUT',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            ...config.headers
        },
        credentials: 'same-origin'
    };

    return fetch(getUrl(url), options)
        .then(response => checkStatus(options, response))
        .then(response => parse(options, response));
}

export default {
    get,
    post,
    put,
    setUrlPrefix
};

