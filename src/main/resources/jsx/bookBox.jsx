import React from "react";
import BookForm from "./bookForm";
import BookList from "./bookList";

export default class BookBox extends React.Component {
    constructor(props) {
        super(props);
        this.handleBookSubmit = this.handleBookSubmit.bind(this);
        this.loadBooksFromServer = this.loadBooksFromServer.bind(this);
        this.state = {data: props.data};
    }

    handleBookSubmit(book) {
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
    }

    loadBooksFromServer() {
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
    }

    componentDidMount() {
        this.loadBooksFromServer();
        setInterval(this.loadBooksFromServer, this.props.pollInterval);
    }

    render() {
        return (
            <div className="bookBox row">
                <h2>Best Books ever!</h2>
                <BookList data={this.state.data}/>
                <BookForm onBookSubmit={this.handleBookSubmit}/>
            </div>
        );
    }
}
