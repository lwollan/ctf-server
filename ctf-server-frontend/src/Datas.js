import Ajax from './Ajax';
import Immutable from 'immutable';

const delayPromise = timeout => new Promise(resolve => setTimeout(resolve, timeout));

//Ajax.setUrlPrefix('http://localhost:9090/');
Ajax.setUrlPrefix('https://ss-ctf-server.herokuapp.com/');

function getTeamFromLocalStorage() {
    try {
        return JSON.parse(localStorage.getItem('team'));
    } catch (e) {
        return null;
    }
}

class Datas {

    state = Immutable.fromJS({ team: getTeamFromLocalStorage() });
    subscribers = [];

    pollBoard() {
        this.getBoard()
            .then(() => delayPromise(5000))
            .then(() => this.pollBoard());
    }

    getBoard() {
        return Ajax.get('api/public/board/')
            .then(res => this.state = this.state.set('board', Immutable.fromJS(res)))
            .then(() => this.notify());
    }

    pollFlags() {
        if (!this.state.get('team')) {
            return;
        }

        this.getFlags()
            .then(() => delayPromise(5000))
            .then(() => this.pollFlags());
    }

    getTeamKeyHeader() {
        return { headers: { 'X-TEAM-KEY': this.state.getIn(['team', 'X-TEAM-KEY']) } };
    }

    getFlags() {
        return Ajax.get('api/flag/list/', this.getTeamKeyHeader())
            .then(res => this.state = this.state.set('flags', Immutable.fromJS(res)))
            .then(() => this.notify());
    }

    postFlag(flagId, flag) {
        return Ajax.post('api/flag', { flagId, flag }, this.getTeamKeyHeader())
            .then(() => this.getFlags());
    }

    loginTeam(team) {
        localStorage.setItem('team', JSON.stringify(team));

        this.state = this.state
            .set('team', Immutable.fromJS(team));
    }

    registerTeam(name) {
        return Ajax.post(`api/team/add/${name}`)
            .then(res => this.loginTeam({ name, ...res }))
            .then(() => this.notify());
    }

    notify() {
        this.subscribers.forEach(sub => sub(this.state));
    }

    logout = () => {
        this.state = this.state.set('team', null);
        localStorage.removeItem('team');
        this.notify();
    };

    subscribe(subFn) {
        subFn(this.state);

        this.subscribers.push(subFn);
    }

    unsubscribe(subFn) {
        const index = this.subscribers.indexOf(subFn);

        this.subscribers.splice(index, 1);
    }
}

export default new Datas();