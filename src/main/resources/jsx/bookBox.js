var converter = new Showdown.converter();

var BookForm = React.createClass({
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
            <form className="bookForm well" onSubmit={this.handleSubmit}>
                <h4>Add a new book:</h4>
                <input type="text" placeholder="Author" ref="author" className="form-control" />
                <input type="text" placeholder="Title" ref="title" className="form-control"/>
                <button type="submit" className="btn btn-primary">Add book</button>
            </form>
        );
    }
});

var Book = React.createClass({
    render: function () {
        var rawMarkup = converter.makeHtml(this.props.children.toString());
        return (
            <div className="book">
                <h3>{this.props.author}</h3>
                <div className="lead" dangerouslySetInnerHTML={{__html: rawMarkup}} />
            </div>
        );
    }
});

var BookList = React.createClass({
    render: function () {
        var bookNodes = this.props.data.map(function (book, index) {
            return (
                <Book author={book.author} key={index}>
                    {book.title}
                </Book>
            );
        });
        return (
            <div className="bookList">
                {bookNodes}
            </div>
        );
    }
});

var BookBox = React.createClass({
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
            <div className="bookBox">
                <h1>Best Books ever!</h1>
                <BookList data={this.state.data} />
                <BookForm onBookSubmit={this.handleBookSubmit} />
            </div>
        );
    }
});

var renderClient = function (books) {
    var data = books || [];
    React.render(
        <BookBox data={data} url='books.json' pollInterval={5000} />,
        document.getElementById("content")
    );
};

var renderServer = function (books) {
    var data = Java.from(books);
    return React.renderToString(
        <BookBox data={data} url='books.json' pollInterval={5000} />
    );
};