import React, { Component } from 'react';

import Datas from './Datas';

export default class Flag extends Component {

    state = {};

    postFlag = () => {
        const { value } = this.inputNode;
        const { flag } = this.props;

        if (!value || !value.trim()) {
            return;
        }

        Datas.postFlag(flag.get('flagId'), value)
            .catch(() => this.setState({ incorrect: true }));
    };

    render() {
        const { flag } = this.props;
        const { incorrect } = this.state;

        return (
            <section className={ `flag ${incorrect ? 'incorrect' : ''}` }>
                <header>{ flag.get('flagName') }</header>
                <div className="flag-content">
                    <textarea className="flag-answer" ref={ node => this.inputNode = node }/>
                    <div className="flag-actions">
                        <button className="flag-action" onClick={ this.postFlag }>Submit</button>
                    </div>
                </div>
            </section>
        );
    }
}