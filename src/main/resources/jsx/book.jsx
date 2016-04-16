import React from "react";

export default class Book extends React.Component {
    render() {
        return (
            <div className="book">
                <h3>{this.props.author}</h3>
                <div className="lead" dangerouslySetInnerHTML={{__html: this.props.children.toString()}}></div>
            </div>
        );
    }
}
