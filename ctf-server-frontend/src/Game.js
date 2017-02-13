import React, { Component } from 'react';
import Datas from './Datas';
import Register from './Register';
import Flags from './Flags';

export default class Game extends Component {

    state = {};

    componentWillMount() {
        Datas.subscribe(this.unsub = state => this.setState({ team: state.get('team') }));
    }

    componentWillUnmount() {
        Datas.unsubscribe(this.unsub);
    }

    render() {
        const { team } = this.state;

        if (!team) {
            return <Register />
        }

        return (
            <div>
                <div className="block">
                    <div>game on lol { team.get('name') }</div>
                    <button type="button" onClick={ Datas.logout }>logout</button>
                </div>
                <Flags />
            </div>
        );
    }
}
