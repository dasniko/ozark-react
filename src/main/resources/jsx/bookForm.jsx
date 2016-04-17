import React from "react";

export default class BookForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleSubmit(e) {
        e.preventDefault();
        var author = this.refs.author.value.trim();
        var title = this.refs.title.value.trim();
        if (!author || !title) {
            return;
        }
        this.props.onBookSubmit({author: author, title: title});
        this.refs.author.value = '';
        this.refs.title.value = '';
    }

    render() {
        return (
            <div className="card col s6">
                <form className="bookForm card-content" onSubmit={this.handleSubmit}>
                    <div className="card-title">Add a new book:</div>
                    <div className="input-field">
                        <input type="text" id="author" ref="author" className="validate"/>
                        <label htmlFor="author">Author</label>
                    </div>
                    <div className="input-field">
                        <input type="text" id="title" ref="title" className="validate"/>
                        <label htmlFor="title">Title</label>
                    </div>
                    <button type="submit" className="btn waves-effect waves-light">Add book
                        <i className="material-icons right">send</i>
                    </button>
                </form>
            </div>
        );
    }
}
