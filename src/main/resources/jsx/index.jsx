import React from "react";
import ReactDOM from "react-dom";
import ReactDOMServer from "react-dom/server";
import BookBox from "./bookBox";

let renderClient = function (books) {
    var data = books || [];
    if (typeof window !== 'undefined') {
        ReactDOM.render(
            <BookBox data={data} url='books.json' pollInterval={5000}/>,
            document.getElementById("content")
        );
    }
};

let renderServer = function (books) {
    var data = Java.from(books);
    return ReactDOMServer.renderToString(
        <BookBox data={data} url='books.json' pollInterval={5000} />
    );
};

module.exports = {
    renderClient: renderClient,
    renderServer: renderServer
};
