import React from "react";
import Book from "./book";

export default class BookList extends React.Component {
    render() {
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
}
