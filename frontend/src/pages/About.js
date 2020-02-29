import React from 'react';

import "./About.scss";

export default function App() {
  return(
    <div className="about">
      <h3>Instagram++</h3>

      <div className="description">
        Instagram++ is an picture-posting app developped by a team of six second-year software engineering students.
        It allows users to register and create an account, as well as post pictures once logged in.
        Users can follow each other and view other user's profiles at their leisure.
        They also have the ablity to like and comment on posts. 
      </div>
      
      <h4>Authors</h4>
      <div className="authors">
        <a target="_blank" rel="noopener noreferrer" href="https://github.com/mint47">Minh-Tam Do</a>
        <a target="_blank" rel="noopener noreferrer" href="https://github.com/Karin-kazarian">Karin Kazarian</a>
        <a target="_blank" rel="noopener noreferrer" href="https://github.com/nasaku898">Simon Lim</a>
        <a target="_blank" rel="noopener noreferrer" href="https://github.com/emilie-martin">Émilie Martin</a>
        <a target="_blank" rel="noopener noreferrer" href="https://github.com/1999lucnguyen">Luc Nguyen</a>
        <a target="_blank" rel="noopener noreferrer" href="https://github.com/daniela-venuta">Daniela Venuta</a>
      </div>
    </div>
  );
}
