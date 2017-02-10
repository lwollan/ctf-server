import React, { Component } from 'react';
import Datas from './Datas';
import Register from './Register';

export default class Game extends Component {

    state = {};

    componentWillMount() {
        Datas.subscribe(state => this.setState({ team: state.get('team') }));
    }

    render() {
        const { team } = this.state;

        if (!team) {
            return <Register />
        }

        return (
            <div>
                <div>game on lol</div>
                <button type="button" onClick={ Datas.logout }>logout</button>
            </div>
        );
    }
}
