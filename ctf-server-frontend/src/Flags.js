import React, { Component } from 'react';
import Datas from './Datas';
import Flag from './Flag';

export default class Flags extends Component {

    componentWillMount() {
        Datas.subscribe(this.unsub = state => this.setState({ flags: state.get('flags') }));

        Datas.pollFlags();
    }

    componentWillUnmount() {
        Datas.unsubscribe(this.unsub);
    }

    render() {
        const { flags } = this.state;

        if (!flags) {
            return <div className="block">Loading flags</div>;
        }

        return (
            <div className="block flags">
                { flags.map(flag => <Flag key={ flag.get('flagId') } flag={ flag } />) }
            </div>
        );
    }
}
