<template>
    <div id="app">
        <Header class="top-header"></Header>
        <BaseHeading class="base-header" name=""></BaseHeading>
        <h2 class="h2-user-guide"> Benutzerhandbuch </h2>

        <div class="generalInfo">
            <h2 class="h2-general-info">Allgemeine Information</h2>
            <b-col class="col-for-mobile">
                <Accordion
                    v-for="(generalInfo, i) in generalInfos"
                    :general-info="generalInfo"
                    :index="i"
                    :key="i"
                    :open="generalInfo.open"
                    @toggleOpen="toggleOpen"
                />
            </b-col>
        </div>

    </div>
</template>

<script>
import Accordion from "@/main/vue/components/Accordion";
import Header from "@/main/vue/components/Header";
export default {
    name: "UserGuide",
    components: {Header, Accordion},
    data: function () {
        return {
            generalInfos: [

                {
                    open: false,
                    question: 'My journey with Vue',
                    answer: 'QUnit is by calling one of the object that are embedded in JavaScript\n'
                },
                {
                    open: false,
                    question: 'Blogging with Vue',
                    answer: 'QUnit is by calling one of the object that are embedded in JavaScript\n'

                },
                {
                    open: false,
                    question: 'Why Vue is so fun',
                    answer: 'QUnit is by calling one of the object that are embedded in JavaScript\n'

                },
                // Section for Signature
                {
                    open: false,
                    question: "Wie unterschreibe ich ein Dokument?",
                    answer: "halloooo"
                },
                {
                    open: false,
                    question: "Ist das digitale Unterschreiben überhaupt sicher?",
                    answer: "Halloooo 2"
                },
                {
                    open: false,
                    question: "Wie funktioniert die kryptographische Signatur?",
                    answer: "halloooo 3"
                }
            ],
        }
    },
    methods: {
        toggleOpen: function (index) {
            this.generalInfos = this.generalInfos.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;
                } else {
                    generalInfo.open = false;
                }
                return generalInfo;
            });

        }
    }
}
</script>

<style>

.base-header {
    margin-top: 2.2em;
}

.h2-user-guide {
    display: flex;
    align-items: center;
    margin-top: 3em;
    font-size: 2em;
    margin-left: 5rem;
    text-align: left;
}
/* for the line after the headline */
.h2-user-guide::after {
    content: '';
    flex: 1;
    margin-left: .5rem;
    height: .1rem;
    width: 100%;
    background-color: #000;
}
.h2-general-info {
    margin-left: 3.8em;
    font-size: 1.6em;
    margin-top: 1.2em;
    color: #436495;
}
.generalInfo .header-section-2 {
    font-size: 1.4em;
}

.generalInfo {
    text-align: left;
}

.generalInfo .question .h2-head {
    font-size: .8em;
    text-align: left;
    margin-left: 10em;
    margin-top: -20px;
}


body {
    background-color: #EEE;
    font-family: sans-serif;
}

.generalInfo {
    width: 100%;
    max-width: 768px;
    background: white;
}
.generalInfo .question {
    alignment: left;
    color: #3c3c3c;
    font-size: 30px;
    margin-top: -1em;
    transition: all 0.4s linear;
    margin-left: -1.3em;
    cursor: pointer;
}

/* for screen width which are equal 500px or less*/
@media only screen and (max-width: 500px) {
    /* For the Right Arrow (Go Back arrow)*/
    .base-header {
        margin-top: 3em;
    }

    .top-header {
        height: 7%;
    }

    /* for user guide headers */
    .h2-user-guide {
        margin-top: 2.4em;
        margin-left: 2em;
        font-size: 1.8em;
    }

    .generalInfo {
        margin-left: -1.4em;
    }

    /* for faq section (general infos) */
    .h2-general-info {
        font-size: 1.6em;
        margin-top: 1.6em;
    }
    /* for faq headers */
    .generalInfo .question{
        font-size: 1.5em;
        margin-left: -4em;
        transition: top 4s ease 0s;

    }
    /* for faq answer of the headers */
    .generalInfo .answer {
        font-size: 1em;
        margin-left: 10em;
        overflow-y: hidden;
        text-align: left;
        opacity: 0;
        max-height: 0;
        transition: top 4s ease 0s;
    }
    /* for faq answer for the open cause (collapsed) */
    .generalInfo .open .answer {
        opacity: 1;
        font-size: .9em;
        max-height: 1000px;
        margin-left: 7em;
        transform: translateY(-10%);

    }
    /* for the icon in the faq header */
    .generalInfo .question .ui-icon-caret-1-n {
        content: '';
        margin-left: 7em;
        transform: translateY(0%) rotate(0deg);
        width: 10px;
        height: 10px;
        color: #436495;
    }
    /* for the icon in the faq header open cause (collapsed) */
    .generalInfo .open .question .ui-icon-caret-1-n {
        transform: translateY(5%) rotate(90deg);
        color: #436495;
        width: 10px;
        height: 10px;
    }
    /* for the faq section (signaure) */
    .generalInfo .header-section-2 {
        font-size: 10em;
    }
}

.generalInfo .open .question {
    margin-bottom: 0px;
}
/* for screen width of 550px or greater than 550px */
@media screen and (min-width: 550px ) {
    .top-header {
        height: 10%;
    }
    .generalInfo .col-for-mobile {
        margin-left: -3em;
    }
    .generalInfo .header-section-2 {
        padding-left: 2em;
    }
    .generalInfo .question {
        font-size: 1.5em;
    }
    .generalInfo .question .ui-icon-caret-1-n {
        content: '';
        margin-left: 7em;
        transform: translateY(30%) rotate(0deg);
        width: 15px;
        height: 15px;
        color: #436495;
    }
    .generalInfo .open .answer {
        margin-left: 11.4em;
        font-size: .9em;
    }
}

.generalInfo .answer {
    color: #3c3c3c;
    font-size: 18px;
    margin-left: 15.5rem;
    text-align: left;
    opacity: 0;
    max-height: 0;
    overflow-y: hidden;
    /*transition: all 0.5s ease-in-out;*/
}
.generalInfo .open .answer {
    opacity: 1;
    max-height: 1000px;
}
</style>
