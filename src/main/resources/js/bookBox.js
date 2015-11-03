var converter = new Showdown.converter();

var BookForm = React.createClass({displayName: "BookForm",
    handleSubmit: function (e) {
        e.preventDefault();
        var author = this.refs.author.getDOMNode().value.trim();
        var title = this.refs.title.getDOMNode().value.trim();
        if (!author || !title) {
            return;
        }
        this.props.onBookSubmit({author: author, title: title});
        this.refs.author.getDOMNode().value = '';
        this.refs.title.getDOMNode().value = '';
    },
    render: function () {
        return (
            React.createElement("form", {className: "bookForm well", onSubmit: this.handleSubmit},
                React.createElement("h4", null, "Add a new book:"),
                React.createElement("input", {type: "text", placeholder: "Author", ref: "author", className: "form-control"}),
                React.createElement("input", {type: "text", placeholder: "Title", ref: "title", className: "form-control"}),
                React.createElement("button", {type: "submit", className: "btn btn-primary"}, "Add book")
            )
        );
    }
});

var Book = React.createClass({displayName: "Book",
    render: function () {
        var rawMarkup = converter.makeHtml(this.props.children.toString());
        return (
            React.createElement("div", {className: "book"},
                React.createElement("h3", null, this.props.author),
                React.createElement("div", {className: "lead", dangerouslySetInnerHTML: {__html: rawMarkup}})
            )
        );
    }
});

var BookList = React.createClass({displayName: "BookList",
    render: function () {
        var bookNodes = this.props.data.map(function (book, index) {
            return (
                React.createElement(Book, {author: book.author, key: index},
                    book.title
                )
            );
        });
        return (
            React.createElement("div", {className: "bookList"},
                bookNodes
            )
        );
    }
});

var BookBox = React.createClass({displayName: "BookBox",
    handleBookSubmit: function (book) {
        var books = this.state.data;
        books.push(book);
        this.setState({data: books}, function () {
            $.ajax({
                url: this.props.url,
                contentType: 'application/json',
                dataType: 'json',
                type: 'POST',
                data: JSON.stringify(book),
                success: function (data) {
                    this.setState({data: data});
                }.bind(this),
                error: function (xhr, status, err) {
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
        });
    },
    loadBooksFromServer: function () {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            success: function (data) {
                this.setState({data: data});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    getInitialState: function () {
        return {data: this.props.data};
    },
    componentDidMount: function () {
        this.loadBooksFromServer();
        setInterval(this.loadBooksFromServer, this.props.pollInterval);
    },
    render: function () {
        return (
            React.createElement("div", {className: "bookBox"},
                React.createElement("h1", null, "Best Books ever!"),
                React.createElement(BookList, {data: this.state.data}),
                React.createElement(BookForm, {onBookSubmit: this.handleBookSubmit})
            )
        );
    }
});

var renderClient = function (books) {
    var data = books || [];
    React.render(
        React.createElement(BookBox, {data: data, url: "books.json", pollInterval: 5000}),
        document.getElementById("content")
    );
};

var renderServer = function (books) {
    var data = Java.from(books);
    return React.renderToString(
        React.createElement(BookBox, {data: data, url: "books.json", pollInterval: 5000})
    );
};